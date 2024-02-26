package com.contract.hopdong.hopdongpheduyet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;

// import java.util.List;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
// import com.contract.hopdong.hopdongpheduyet.model.TrangThaiPheDuyet;

@Repository
public interface HopDongPheDuyetRepository extends JpaRepository<HopDongPheDuyetEntity, Long> {
  // HopDongPheDuyetEntity findByHopDongId(Long id);

  // HopDongPheDuyetEntity findByHopDongIdAndTrangThaiPheDuyetIsIn(Long id,
  // List<TrangThaiPheDuyet> trangThaiPheDuyet);

  // List<HopDongPheDuyetEntity> findByGroupIdByTimestamp(String
  // groupIdByTimestamp);
  @EntityGraph(attributePaths = { "hopDongPheDuyetNguoiNhanEntityList",
      "hopDongPheDuyetNguoiNhanEntityList.nguoiDungEntity", "hopDongPheDuyetTienTrinhEnities",
      "hopDongPheDuyetTienTrinhEnities.nguoiDungEntity" })
  @Query("select a from HopDongPheDuyetEntity a where a.hopDongId = :hopDongId order by a.createdAt desc")
  List<HopDongPheDuyetEntity> findAllByHopDongId(@Param("hopDongId") Long hopDongId);

  // // user
  // @Query("select distinct a " +
  // "from HopDongPheDuyetEntity a " +
  // "left join a.hopDongPheDuyetNguoiDungEntityList b " +
  // "left join a.hopDongEntity " +
  // "left join a.nguoiGuiEntity " +
  // "where b.nguoiDungId = :id " +
  // "and (coalesce(:trangThaiPheDuyet) is null or a.trangThaiPheDuyet in
  // :trangThaiPheDuyet) " +
  // "and (:hopDongId is null or a.hopDongId = :hopDongId)")
  // Page<HopDongPheDuyetEntity> findByNguoiNhan(@Param("id") Long id,
  // @Param("trangThaiPheDuyet") List<TrangThaiPheDuyet> trangThaiPheDuyet,
  // @Param("hopDongId") Long hopDongId, Pageable pageable);

  // @Query("select distinct a " +
  // "from HopDongPheDuyetEntity a " +
  // "left join a.hopDongPheDuyetNguoiDungEntityList " +
  // "left join a.hopDongEntity b " +
  // "left join b.tramEntity c " +
  // "left join a.nguoiGuiEntity " +
  // "where " +
  // "(coalesce(:trangThaiPheDuyet) is null or a.trangThaiPheDuyet in
  // :trangThaiPheDuyet) " +
  // "and c.phongDaiId in :ids " +
  // "and (:hopDongId is null or a.hopDongId = :hopDongId) " +
  // "order by a.createdAt desc ")
  // Page<HopDongPheDuyetEntity> findByPhongDai(@Param("ids") List<Integer> ids,
  // @Param("trangThaiPheDuyet") List<TrangThaiPheDuyet> trangThaiPheDuyet,
  // @Param("hopDongId") Long hopDongId, Pageable pageable);
  // // admin

  // @Query("select distinct a " +
  // "from HopDongPheDuyetEntity a " +
  // "left join a.hopDongPheDuyetNguoiDungEntityList " +
  // "left join a.hopDongEntity b " +
  // "left join b.tramEntity c " +
  // "left join a.nguoiGuiEntity " +
  // "where " +
  // "(coalesce(:trangThaiPheDuyet) is null or a.trangThaiPheDuyet in
  // :trangThaiPheDuyet) " +
  // "and (:hopDongId is null or a.hopDongId = :hopDongId) " +
  // "order by a.createdAt desc ")
  // Page<HopDongPheDuyetEntity> adminFindByPhongDai(
  // @Param("trangThaiPheDuyet") List<TrangThaiPheDuyet> trangThaiPheDuyet,
  // @Param("hopDongId") Long hopDongId, Pageable pageable);
}
