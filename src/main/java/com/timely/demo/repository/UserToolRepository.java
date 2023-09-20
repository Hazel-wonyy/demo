package com.timely.demo.repository;


import com.timely.demo.common.config.security.model.ProviderType;
import com.timely.demo.common.entity.UserEntity;
import com.timely.demo.common.entity.UserToolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserToolRepository extends JpaRepository<UserToolEntity, Long> {

    List<UserToolEntity> findAllByUserId(Long userId);

}
