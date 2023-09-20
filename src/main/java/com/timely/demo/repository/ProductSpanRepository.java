package com.timely.demo.repository;


import com.timely.demo.common.entity.ProductSpanEntity;
import com.timely.demo.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpanRepository extends JpaRepository<ProductSpanEntity, Long> {

}
