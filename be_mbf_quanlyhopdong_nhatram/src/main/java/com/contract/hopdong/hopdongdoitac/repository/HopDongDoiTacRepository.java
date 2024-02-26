package com.contract.hopdong.hopdongdoitac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongdoitac.entity.HopDongDoiTacEntity;

@Repository
public interface HopDongDoiTacRepository extends JpaRepository<HopDongDoiTacEntity, Long> {

    @Query("select distinct a from HopDongDoiTacEntity a where a.hopDongId=:hopDongId")
    HopDongDoiTacEntity findByHopDongId(@Param("hopDongId") Long hopDongId);
}
