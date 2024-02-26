package com.contract.danhmuc.thue.repository;

import com.contract.danhmuc.thue.entity.DmThueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DmThueRepository extends JpaRepository<DmThueEntity, Integer> {
    DmThueEntity findBySoThue(float soThue);
}