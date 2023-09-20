package com.timely.demo.repository;


import com.timely.demo.common.entity.MytoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MytoolRepository extends JpaRepository<MytoolEntity, Long> {

}
