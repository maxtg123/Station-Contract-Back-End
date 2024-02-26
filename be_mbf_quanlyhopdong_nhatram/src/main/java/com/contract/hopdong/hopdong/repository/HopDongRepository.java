package com.contract.hopdong.hopdong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdong.entity.HopDongEntity;

@Repository
public interface HopDongRepository extends JpaRepository<HopDongEntity, Long> {

        @Query("SELECT DISTINCT a from HopDongEntity a"
                        + " WHERE (:id is null or a.id != :id)"
                        + " AND (a.soHopDong = :soHopDong OR (a.soHopDongErp IS NOT NULL AND a.soHopDongErp = :soHopDongErp))")
        List<HopDongEntity> findByIdAndSoHdOrSoHdErp(@Param("id") Long id,
                        @Param("soHopDong") String soHopDong, @Param("soHopDongErp") String soHopDongErp);

        @Query("SELECT DISTINCT a from HopDongEntity a"
                        + " WHERE (:id is null or a.id != :id)"
                        + " AND (a.soHopDongErp = :soHopDongErp)")
        List<HopDongEntity> findByIdAndSoHdErp(@Param("id") Long id, @Param("soHopDongErp") String soHopDongErp);

        @EntityGraph(attributePaths = { "hopDongDoiTacEntity", "hopDongTramEntities", "dmHinhThucKyHopDongEntity",
                        "dmHinhThucDauTuEntity", "dmKhoanMucEntity", "dmDoiTuongKyHopDongEntity",
                        "dmHinhThucThanhToanEntity", "hopDongFileEntities", "hopDongPhuLucEntities",
                        "hopDongFileEntities",
                        "hopDongTramEntities.tramEntity", "hopDongTramEntities.tramEntity.dmPhongDaiEntity",
                        "hopDongTramEntities.tramEntity.dmToEntity", "hopDongTramEntities.tramEntity.dmTinhEntity",
                        "hopDongTramEntities.tramEntity.dmHuyenEntity",
                        "hopDongTramEntities.tramEntity.dmXaEntity",
                        "hopDongTramEntities.tramEntity.dmTramKhuVucEntity",
                        "hopDongTramEntities.tramEntity.dmLoaiCshtEntity",
                        "hopDongTramEntities.tramEntity.dmLoaiTramEntity",
                        "hopDongTramEntities.tramEntity.dmLoaiCotAngtenEntity",
                        "hopDongTramEntities.hopDongTramPhuTroEntities",
                        "hopDongTramEntities.hopDongTramPhuTroEntities.dmLoaiHopDongPhuTroEntity",
                        "hopDongTramEntities.hopDongTramKyThanhToanEntities",
                        "hopDongTramEntities.hopDongTramDungChungEntity",
                        "hopDongTramEntities.hopDongTramDungChungEntity.dmLoaiHangMucCSHTEntity",
                        "hopDongTramEntities.hopDongTramDungChungEntity.dmDonViDungChungEntity" })
        @Query("SELECT DISTINCT a FROM HopDongEntity a WHERE a.id = :id")
        HopDongEntity findByIdWithFetch(@Param("id") Long id);

        @Query("select distinct a from HopDongEntity a where a.soHopDong =:soHopDong")
        HopDongEntity findBySoHopDongWithoutFetch(@Param("soHopDong") String soHopDong);

        @EntityGraph(attributePaths = { "hopDongTramEntities", "hopDongDoiTacEntity" })
        @Query("select distinct a from HopDongEntity a left join a.hopDongTramEntities")
        List<HopDongEntity> findAllWithoutFetch();

        @Query("select distinct a from HopDongEntity a")
        List<HopDongEntity> findAllOnlyHopDongEntity();

        @EntityGraph(attributePaths = { "hopDongDoiTacEntity", "hopDongTramEntities",
                        "hopDongTramEntities.tramEntity", "hopDongTramEntities.tramEntity.dmPhongDaiEntity" })
        @Query("select distinct a from HopDongEntity a")
        List<HopDongEntity> findAllHopDongAndDoiTacAndTramsPhongDai();

        @EntityGraph(attributePaths = { "hopDongTramEntities" })
        @Query("select distinct a from HopDongEntity a where a.soHopDong =:soHD")
        HopDongEntity findBySoHopDong(@Param("soHD") String soHD);

        @EntityGraph(attributePaths = { "hopDongTramEntities" })
        @Query("select distinct a from HopDongEntity a where a.soHopDongErp =:soHDErp")
        HopDongEntity findBySoHopDongErpAndFetchTram(@Param("soHDErp") String soHDErp);

        @Query("select count (distinct a) from HopDongEntity a where a.doiTuongKyId =:id")
        long countByDoiTuongKyId(@Param("id") Integer id);

        @Query("select count (distinct a) from HopDongEntity a where a.hinhThucKyId =:id")
        long countByHinhThucKyId(@Param("id") Integer id);

        @Query("select count (distinct a) from HopDongEntity a where a.hinhThucThanhToanId =:id")
        long countByHinhThucThanhToanId(@Param("id") Integer id);

        @Query("select count (distinct a) from HopDongEntity a where a.hinhThucDauTuId =:id")
        long countByHinhThucDauTuId(@Param("id") Integer id);

        @EntityGraph(attributePaths = { "hopDongTramEntities", "hopDongDoiTacEntity", "hopDongTramEntities",
                        "dmHinhThucKyHopDongEntity",
                        "dmHinhThucDauTuEntity", "dmDoiTuongKyHopDongEntity",
                        "dmHinhThucThanhToanEntity", "hopDongFileEntities", "hopDongPhuLucEntities",
                        "hopDongFileEntities",
                        "hopDongTramEntities.tramEntity", "hopDongTramEntities.tramEntity.dmPhongDaiEntity",
                        "hopDongTramEntities.tramEntity.dmToEntity", "hopDongTramEntities.tramEntity.dmTinhEntity",
                        "hopDongTramEntities.tramEntity.dmHuyenEntity",
                        "hopDongTramEntities.tramEntity.dmXaEntity",
                        "hopDongTramEntities.tramEntity.dmTramKhuVucEntity",
                        "hopDongTramEntities.tramEntity.dmLoaiCshtEntity",
                        "hopDongTramEntities.tramEntity.dmLoaiTramEntity",
                        "hopDongTramEntities.tramEntity.dmLoaiCotAngtenEntity",
                        "hopDongTramEntities.hopDongTramPhuTroEntities",
                        "hopDongTramEntities.hopDongTramPhuTroEntities.dmLoaiHopDongPhuTroEntity",
                        "hopDongTramEntities.hopDongTramKyThanhToanEntities",
                        "hopDongTramEntities.hopDongTramDungChungEntity",
                        "hopDongTramEntities.hopDongTramDungChungEntity.dmLoaiHangMucCSHTEntity" })
        @Query("select distinct a from HopDongEntity a left join  a.hopDongTramEntities b where b.tramId =:tramId order by a.ngayKy desc ")
        List<HopDongEntity> findHopDongByIdTram(@Param("tramId") Long tramId);

        @EntityGraph(attributePaths = { "hopDongDoiTacEntity", "hopDongTramEntities", "dmHinhThucKyHopDongEntity",
                        "dmHinhThucDauTuEntity", "dmKhoanMucEntity", "dmDoiTuongKyHopDongEntity",
                        "dmHinhThucThanhToanEntity", "hopDongFileEntities", "hopDongPhuLucEntities",
                        "hopDongFileEntities",
                        "hopDongTramEntities.tramEntity", "hopDongTramEntities.tramEntity.dmPhongDaiEntity",
                        "hopDongTramEntities.tramEntity.dmToEntity", "hopDongTramEntities.tramEntity.dmTinhEntity",
                        "hopDongTramEntities.tramEntity.dmHuyenEntity",
                        "hopDongTramEntities.tramEntity.dmXaEntity",
                        "hopDongTramEntities.tramEntity.dmTramKhuVucEntity",
                        "hopDongTramEntities.tramEntity.dmLoaiCshtEntity",
                        "hopDongTramEntities.tramEntity.dmLoaiTramEntity",
                        "hopDongTramEntities.tramEntity.dmLoaiCotAngtenEntity",
                        "hopDongTramEntities.hopDongTramPhuTroEntities",
                        "hopDongTramEntities.hopDongTramPhuTroEntities.dmLoaiHopDongPhuTroEntity",
                        "hopDongTramEntities.hopDongTramKyThanhToanEntities",
                        "hopDongTramEntities.hopDongTramDungChungEntity",
                        "hopDongTramEntities.hopDongTramDungChungEntity.dmLoaiHangMucCSHTEntity",
                        "hopDongTramEntities.hopDongTramDungChungEntity.dmDonViDungChungEntity" })
        @Query("select distinct a from HopDongEntity a left join  a.hopDongTramEntities b where b.id =:hopDongTramId")
        HopDongEntity findByHopDongTramIdWithFetch(@Param("hopDongTramId") Long hopDongTramId);
}
