package com.timely.demo.common.config.security;

import com.timely.demo.common.config.security.model.UserPrincipal;
import com.timely.demo.common.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public boolean isLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null;
    }

    public UserEntity getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userDetails = (UserPrincipal) auth.getPrincipal();
        return UserEntity.builder().userId(userDetails.getIuser()).build();
    }

    //로그인한 유저의 pk값 구하기
    public Long getLoginUserPk() {
        return getLoginUser().getUserId();
    }
}