package com.contract.hopdong.hopdongphuluc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongphuluc.entity.HopDongPhuLucEntity;

@Repository
public interface HopDongPhuLucRepository extends JpaRepository<HopDongPhuLucEntity, Long> {
  @Query("select distinct a from HopDongPhuLucEntity a where a.soPhuLuc =:soPhuLuc")
  HopDongPhuLucEntity findBySoPhuLuc(@Param("soPhuLuc") String soPhuLuc);
}
