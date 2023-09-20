package com.timely.demo.repository;


import com.timely.demo.common.entity.ProductPlaceholderEntity;
import com.timely.demo.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPlaceholderRepository extends JpaRepository<ProductPlaceholderEntity, Long> {

}
