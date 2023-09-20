package com.timely.demo.repository;


import com.timely.demo.common.config.security.model.ProviderType;
import com.timely.demo.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /* delYn여부 추가, 0일 시에만 로그인가능, 1일시에는 탈퇴회원으로 로그인 불가 */
    UserEntity findByProviderTypeAndUidAndDelYn(ProviderType providerType, String uid, Long delYn);

}
