package com.contract.hopdong.hopdongdamphan_nguoinhan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongdamphan_nguoinhan.entity.HopDongDamPhanNguoiNhanEntity;

@Repository
public interface HopDongDamPhanNguoiNhanRepository extends JpaRepository<HopDongDamPhanNguoiNhanEntity, Long> {

  @Query("select distinct a from HopDongDamPhanNguoiNhanEntity a where a.hopDongDamPhanId =:damPhanId")
  List<HopDongDamPhanNguoiNhanEntity> findAllByHopDongDamPhanId(@Param("damPhanId") Long damPhanId);
}
