package com.contract.hopdong.hopdongdamphan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongdamphan.entity.HopDongDamPhanEntity;

@Repository
public interface HopDongDamPhanRepository extends JpaRepository<HopDongDamPhanEntity, Long> {
  @EntityGraph(attributePaths = { "nguoiGuiEntity", "hopDongDamPhanNguoiNhanEntities",
      "hopDongDamPhanNguoiNhanEntities.nguoiDungEntity", "hopDongDamPhanTienTrinhEntities",
      "hopDongDamPhanTienTrinhEntities.nguoiDungEntity",
      "hopDongDamPhanTienTrinhEntities.hopDongDamPhanFileEntities",
      "hopDongDamPhanTienTrinhEntities.hopDongDamPhanTienTrinhChangeEntities",
      "hopDongDamPhanTienTrinhEntities.hopDongDamPhanTienTrinhXetDuyetEntities" })
  @Query("select distinct a from HopDongDamPhanEntity a"
      + " left join a.hopDongDamPhanNguoiNhanEntities b" + " where a.hopDongId = :hopDongId"
      + " order by a.createdAt desc, b.createdAt desc")
  List<HopDongDamPhanEntity> findAllByHopDongId(@Param("hopDongId") Long hopDongId);

  @Query("select a from HopDongDamPhanEntity a where a.id =:id and a.hopDongId =:hopDongId")
  HopDongDamPhanEntity findByIdAndHopDongId(@Param("id") Long id, @Param("hopDongId") Long hopDongId);
}
