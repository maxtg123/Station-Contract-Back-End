// package com.contract.hopdong.hopdongnhatram.repository;

// import com.contract.hopdong.hopdongnhatram.entity.HopDongEntity;
// import com.contract.hopdong.hopdongnhatram.model.TrangThaiHopDong;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.jpa.repository.EntityGraph;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import java.util.Date;
// import java.util.List;
// import java.util.Optional;

// @Repository
// public interface HopDongRepository extends JpaRepository<HopDongEntity, Long>
// {
// //user
// @Query("select distinct a from HopDongEntity a where (:id is null or a.id !=
// :id) and (a.soHopDong = :soHopDong or (:soHopDongErp is not null and
// a.soHopDongErp = :soHopDongErp))")
// List<HopDongEntity> findByIdAndSoHopDong(@Param("id") Long id,
// @Param("soHopDong") String soHopDong, @Param("soHopDongErp") String
// soHopDongErp);

// @EntityGraph(value = "hopdong-entity-graph")
// @Query("select distinct a " + "from HopDongEntity a " + "left join
// a.tramEntity b "
// + "left join a.dmHinhThucKyHopDongEntity c " + "left join
// a.dmHinhThucDauTuEntity d "
// + "left join a.dmDoiTuongKyHopDongEntity e " + "left join
// a.hopDongDoiTacEntity "
// + "left join a.fileEntityList " + "left join a.hopDongPhuTroEntityList "
// + "left join a.hopDongKyThanhToanEntityList " + "left join
// a.hopDongPhuLucEntityList "
// + "left join a.hopDongPheDuyetEntityList "
// + "where (:maTram is null or b.maTram = :maTram) "
// + "and (:phongDaiId is null or b.phongDaiId = :phongDaiId) "
// + "and b.phongDaiId in :ids "
// + "and (:search is null or (b.maTram like %:search% or b.maTramErp like
// %:search% or b.maDauTuXayDung like %:search% or a.soHopDong like %:search% or
// a.soHopDongErp like %:search% )) "
// + "and (:soHopDong is null or a.soHopDong like %:soHopDong%) "
// + "and (:soHopDongErp is null or a.soHopDongErp like %:soHopDongErp%) "
// + "and (:hinhThucDauTu is null or d.id = :hinhThucDauTu) "
// + "and (:hinhThucKyHopDongId is null or c.id = :hinhThucKyHopDongId) "
// + "and (:doiTuongKyHopDongId is null or e.id = :doiTuongKyHopDongId) "
// + "and (:trangThaiHopDong is null or a.trangThai = :trangThaiHopDong) "
// + "and ((:ngayKyFrom is null and :ngayKyTo is null) or (a.ngayKy >=
// :ngayKyFrom and a.ngayKy <= :ngayKyTo)) "
// + "and ((:ngayKetThucFrom is null and :ngayKetThucTo is null) or
// (a.ngayKetThuc >= :ngayKetThucFrom and a.ngayKetThuc <= :ngayKetThucTo)) "
// + "and (:trangThaiThanhToan is null or (:trangThaiThanhToan =
// 'CHUA_THANH_TOAN' and exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null))) "
// + "and (:tinhTrangThanhToan is null "
// + "or (:tinhTrangThanhToan = 'HET_HAN' and exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and :ngayHienTai > s.denNgay)) "
// + "or (:tinhTrangThanhToan = 'CAN_THANH_TOAN' and not exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and :ngayHienTai > s.denNgay) and exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and s.tuNgay <= :ngayHienTai and s.denNgay >= :ngayHienTai)) "
// + "or (:tinhTrangThanhToan = 'SAP_DEN' and not exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and :ngayHienTai > s.denNgay) and not exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and s.tuNgay <= :ngayHienTai and s.denNgay >= :ngayHienTai) and exists
// (select 1 from HopDongKyThanhToanEntity s where s.hopDongEntity = a and
// s.daThanhToanNgay is null and s.tuNgay > :ngayHienTai and s.tuNgay <=
// :ngayTuongLai)))")
// Page<HopDongEntity> findAllAndFetch(@Param("ids") List<Integer> ids,
// @Param("maTram") String maTram, @Param("search") String search,
// @Param("soHopDong") String soHopDong, @Param("soHopDongErp") String
// soHopDongErp,
// @Param("hinhThucDauTu") Integer hinhThucDauTu,
// @Param("hinhThucKyHopDongId") Integer hinhThucKyHopDongId,
// @Param("doiTuongKyHopDongId") Integer doiTuongKyHopDongId,
// @Param("ngayKyFrom") Date ngayKyFrom, @Param("ngayKyTo") Date ngayKyTo,
// @Param("ngayKetThucFrom") Date ngayKetThucFrom,
// @Param("ngayKetThucTo") Date ngayKetThucTo,
// @Param("trangThaiThanhToan") String trangThaiThanhToan,
// @Param("tinhTrangThanhToan") String tinhTrangThanhToan,
// @Param("ngayHienTai") Date ngayHienTai,
// @Param("ngayTuongLai") Date ngayTuongLai,
// @Param("phongDaiId") Integer phongDaiId,
// @Param("trangThaiHopDong") TrangThaiHopDong trangThaiHopDong,
// Pageable pageable);

// @Query("select distinct a from HopDongEntity a left join a.tramEntity b where
// b.phongDaiId in :ids")
// List<HopDongEntity> findAllWithoutFetch(@Param("ids") List<Integer> ids, Sort
// sort);

// @Query("select count(distinct a) "
// + "from HopDongEntity a "
// + "left join a.tramEntity b "
// + "where exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null) "
// + "and b.phongDaiId in :ids "
// + "and (:trangThai is null or a.trangThai = :trangThai) "
// + "and (:phongDaiId is null or b.phongDaiId = :phongDaiId) ")
// long countHopDong(@Param("ids") List<Integer> ids, @Param("phongDaiId")
// Integer phongDaiId, @Param("trangThai") TrangThaiHopDong trangThaiHopDong);

// @Query("select count(distinct a) "
// + "from HopDongEntity a "
// + "left join a.tramEntity b "
// + "where exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null) "
// + "and exists (select 1 from HopDongKyThanhToanEntity s where s.hopDongEntity
// = a and s.daThanhToanNgay is null and :ngayHienTai > s.denNgay) "
// + "and b.phongDaiId in :ids "
// + "and (:trangThai is null or a.trangThai = :trangThai) "
// + "and (:phongDaiId is null or b.phongDaiId = :phongDaiId) ")
// long countHopDongQuaHanThanhToan(@Param("ids") List<Integer> ids,
// @Param("ngayHienTai") Date ngayHienTai, @Param("phongDaiId") Integer
// phongDaiId, @Param("trangThai") TrangThaiHopDong trangThaiHopDong);

// @Query("select count(distinct a) "
// + "from HopDongEntity a "
// + "left join a.tramEntity b "
// + "where exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null)"
// + "and exists (select 1 from HopDongKyThanhToanEntity s where s.hopDongEntity
// = a and s.daThanhToanNgay is null and s.tuNgay <= :ngayHienTai and s.denNgay
// >= :ngayHienTai) "
// + "and not exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null and :ngayHienTai >
// s.denNgay) "
// + "and b.phongDaiId in :ids "
// + "and (:trangThai is null or a.trangThai = :trangThai) "
// + "and (:phongDaiId is null or b.phongDaiId = :phongDaiId) ")
// long countHopDongCanThanhToan(@Param("ids") List<Integer> ids,
// @Param("ngayHienTai") Date ngayHienTai, @Param("phongDaiId") Integer
// phongDaiId, @Param("trangThai") TrangThaiHopDong trangThaiHopDong);

// @Query("select count(distinct a) "
// + "from HopDongEntity a "
// + "left join a.tramEntity b "
// + "where exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null)"
// + "and exists (select 1 from HopDongKyThanhToanEntity s where s.hopDongEntity
// = a and s.daThanhToanNgay is null and s.tuNgay > :ngayHienTai and s.tuNgay <=
// :ngayTuongLai) "
// + "and not exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null and s.tuNgay <=
// :ngayHienTai and s.denNgay >= :ngayHienTai) "
// + "and not exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null and :ngayHienTai >
// s.denNgay) "
// + "and b.phongDaiId in :ids "
// + "and (:trangThai is null or a.trangThai = :trangThai) "
// + "and (:phongDaiId is null or b.phongDaiId = :phongDaiId) ")
// long countHopDongChuanBiThanhToan(@Param("ids") List<Integer> ids,
// @Param("ngayHienTai") Date ngayHienTai, @Param("ngayTuongLai") Date
// ngayTuongLai, @Param("phongDaiId") Integer phongDaiId, @Param("trangThai")
// TrangThaiHopDong trangThaiHopDong);

// @EntityGraph(value = "hopdong-entity-graph")
// Optional<HopDongEntity> findById(Long id);

// HopDongEntity findBySoHopDong(String soHopDong);

// //admin
// @Query("select distinct a " + "from HopDongEntity a " + "left join
// a.tramEntity b "
// + "left join a.dmHinhThucKyHopDongEntity c " + "left join
// a.dmHinhThucDauTuEntity d "
// + "left join a.dmDoiTuongKyHopDongEntity e " + "left join
// a.hopDongDoiTacEntity "
// + "left join a.fileEntityList " + "left join a.hopDongPhuTroEntityList "
// + "left join a.hopDongKyThanhToanEntityList " + "left join
// a.hopDongPhuLucEntityList "
// + "left join a.hopDongPheDuyetEntityList "
// + "where (:maTram is null or b.maTram = :maTram) "
// + "and (:phongDaiId is null or b.phongDaiId = :phongDaiId) "
// + "and (:search is null or (b.maTram like %:search% or b.maTramErp like
// %:search% or b.maDauTuXayDung like %:search% or a.soHopDong like %:search% or
// a.soHopDongErp like %:search% )) "
// + "and (:soHopDong is null or a.soHopDong like %:soHopDong%) "
// + "and (:soHopDongErp is null or a.soHopDongErp like %:soHopDongErp%) "
// + "and (:hinhThucDauTu is null or d.id = :hinhThucDauTu) "
// + "and (:hinhThucKyHopDongId is null or c.id = :hinhThucKyHopDongId) "
// + "and (:doiTuongKyHopDongId is null or e.id = :doiTuongKyHopDongId) "
// + "and (:trangThaiHopDong is null or a.trangThai = :trangThaiHopDong) "
// + "and ((:ngayKyFrom is null and :ngayKyTo is null) or (a.ngayKy >=
// :ngayKyFrom and a.ngayKy <= :ngayKyTo)) "
// + "and ((:ngayKetThucFrom is null and :ngayKetThucTo is null) or
// (a.ngayKetThuc >= :ngayKetThucFrom and a.ngayKetThuc <= :ngayKetThucTo)) "
// + "and (:trangThaiThanhToan is null or (:trangThaiThanhToan =
// 'CHUA_THANH_TOAN' and exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null))) "
// + "and (:tinhTrangThanhToan is null "
// + "or (:tinhTrangThanhToan = 'HET_HAN' and exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and :ngayHienTai > s.denNgay)) "
// + "or (:tinhTrangThanhToan = 'CAN_THANH_TOAN' and not exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and :ngayHienTai > s.denNgay) and exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and s.tuNgay <= :ngayHienTai and s.denNgay >= :ngayHienTai)) "
// + "or (:tinhTrangThanhToan = 'SAP_DEN' and not exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and :ngayHienTai > s.denNgay) and not exists (select 1 from
// HopDongKyThanhToanEntity s where s.hopDongEntity = a and s.daThanhToanNgay is
// null and s.tuNgay <= :ngayHienTai and s.denNgay >= :ngayHienTai) and exists
// (select 1 from HopDongKyThanhToanEntity s where s.hopDongEntity = a and
// s.daThanhToanNgay is null and s.tuNgay > :ngayHienTai and s.tuNgay <=
// :ngayTuongLai)))")
// Page<HopDongEntity> adminFindAllAndFetch(@Param("maTram") String maTram,
// @Param("search") String search,
// @Param("soHopDong") String soHopDong, @Param("soHopDongErp") String
// soHopDongErp,
// @Param("hinhThucDauTu") Integer hinhThucDauTu,
// @Param("hinhThucKyHopDongId") Integer hinhThucKyHopDongId,
// @Param("doiTuongKyHopDongId") Integer doiTuongKyHopDongId,
// @Param("ngayKyFrom") Date ngayKyFrom, @Param("ngayKyTo") Date ngayKyTo,
// @Param("ngayKetThucFrom") Date ngayKetThucFrom,
// @Param("ngayKetThucTo") Date ngayKetThucTo,
// @Param("trangThaiThanhToan") String trangThaiThanhToan,
// @Param("tinhTrangThanhToan") String tinhTrangThanhToan,
// @Param("ngayHienTai") Date ngayHienTai,
// @Param("ngayTuongLai") Date ngayTuongLai,
// @Param("phongDaiId") Integer phongDaiId,
// @Param("trangThaiHopDong") TrangThaiHopDong trangThaiHopDong,
// Pageable pageable);

// @Query("select distinct a from HopDongEntity a left join a.tramEntity b")
// List<HopDongEntity> adminFindAllWithoutFetch(Sort sort);

// @Query("select count(distinct a) "
// + "from HopDongEntity a "
// + "left join a.tramEntity b "
// + "where exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null) "
// + "and (:trangThai is null or a.trangThai = :trangThai) "
// + "and (:phongDaiId is null or b.phongDaiId = :phongDaiId) ")
// long adminCountHopDong(@Param("phongDaiId") Integer phongDaiId,
// @Param("trangThai") TrangThaiHopDong trangThaiHopDong);

// @Query("select count(distinct a) "
// + "from HopDongEntity a "
// + "left join a.tramEntity b "
// + "where exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null) "
// + "and exists (select 1 from HopDongKyThanhToanEntity s where s.hopDongEntity
// = a and s.daThanhToanNgay is null and :ngayHienTai > s.denNgay) "
// + "and (:trangThai is null or a.trangThai = :trangThai) "
// + "and (:phongDaiId is null or b.phongDaiId = :phongDaiId) ")
// long adminCountHopDongQuaHanThanhToan(@Param("ngayHienTai") Date ngayHienTai,
// @Param("phongDaiId") Integer phongDaiId, @Param("trangThai") TrangThaiHopDong
// trangThaiHopDong);

// @Query("select count(distinct a) "
// + "from HopDongEntity a "
// + "left join a.tramEntity b "
// + "where exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null)"
// + "and exists (select 1 from HopDongKyThanhToanEntity s where s.hopDongEntity
// = a and s.daThanhToanNgay is null and s.tuNgay <= :ngayHienTai and s.denNgay
// >= :ngayHienTai) "
// + "and not exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null and :ngayHienTai >
// s.denNgay) "
// + "and (:trangThai is null or a.trangThai = :trangThai) "
// + "and (:phongDaiId is null or b.phongDaiId = :phongDaiId) ")
// long adminCountHopDongCanThanhToan(@Param("ngayHienTai") Date ngayHienTai,
// @Param("phongDaiId") Integer phongDaiId, @Param("trangThai") TrangThaiHopDong
// trangThaiHopDong);

// @Query("select count(distinct a) "
// + "from HopDongEntity a "
// + "left join a.tramEntity b "
// + "where exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null)"
// + "and exists (select 1 from HopDongKyThanhToanEntity s where s.hopDongEntity
// = a and s.daThanhToanNgay is null and s.tuNgay > :ngayHienTai and s.tuNgay <=
// :ngayTuongLai) "
// + "and not exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null and s.tuNgay <=
// :ngayHienTai and s.denNgay >= :ngayHienTai) "
// + "and not exists (select 1 from HopDongKyThanhToanEntity s where
// s.hopDongEntity = a and s.daThanhToanNgay is null and :ngayHienTai >
// s.denNgay) "
// + "and (:trangThai is null or a.trangThai = :trangThai) "
// + "and (:phongDaiId is null or b.phongDaiId = :phongDaiId) ")
// long adminCountHopDongChuanBiThanhToan(@Param("ngayHienTai") Date
// ngayHienTai, @Param("ngayTuongLai") Date ngayTuongLai, @Param("phongDaiId")
// Integer phongDaiId, @Param("trangThai") TrangThaiHopDong trangThaiHopDong);
// }
