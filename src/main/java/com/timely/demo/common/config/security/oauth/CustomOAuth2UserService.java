package com.timely.demo.common.config.security.oauth;

import com.timely.demo.common.config.exception.OAuthProviderMissMatchException;
import com.timely.demo.common.config.security.model.ProviderType;
import com.timely.demo.common.config.security.model.RoleType;
import com.timely.demo.common.config.security.model.UserPrincipal;
import com.timely.demo.common.config.security.oauth.userinfo.OAuth2UserInfo;
import com.timely.demo.common.config.security.oauth.userinfo.OAuth2UserInfoFactory;
import com.timely.demo.common.entity.UserEntity;
import com.timely.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository rep;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());

        }
    }

    /** 사용자 체크**/
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        /* 공급자 체크*/
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        /* DB에서 사용자 정보 조회*/
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

        UserEntity savedUser = rep.findByProviderTypeAndUidAndDelYn(providerType, userInfo.getId(), 0L);

        /* provider로 중복체크 */
        if (savedUser != null) {
            if (providerType != savedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + savedUser.getProviderType() + " account to login."
                );
            }
            updateUser(savedUser, userInfo);

            /* 사용자를 찾지 못하면 새로운 사용자 생성*/
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }
    /** 사용자 추가 **/
    private UserEntity createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        UserEntity entity = UserEntity.builder()
                .providerType(providerType)
                .uid(userInfo.getId())
                .unm(userInfo.getName())
                .roleType(RoleType.USER)
                .email(userInfo.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
        rep.save(entity);
        return entity;
    }

    /** 사용자 업데이트**/
    private UserEntity updateUser(UserEntity user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getUnm().equals(userInfo.getName())) {
            user.setUnm(userInfo.getName());
            user.setUpdatedAt(LocalDateTime.now());
        }
        return user;
    }
}
