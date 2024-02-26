package com.contract.tram.tram.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.tram.tram.entity.TramEntity;
import com.contract.tram.tram.model.TrangThaiTram;

@Repository
public interface TramRepository extends JpaRepository<TramEntity, Long> {
        List<TramEntity> findAllByIdIn(Set<Long> ids);

        @EntityGraph(attributePaths = { "dmPhongDaiEntity", "dmToEntity", "dmTinhEntity", "dmHuyenEntity", "dmXaEntity",
                        "dmTramKhuVucEntity", "dmLoaiCshtEntity", "dmLoaiTramEntity", "dmLoaiCotAngtenEntity",
                        "dmLoaiThietBiRanEntityList" })
        @Query("SELECT tram from TramEntity tram" +
                        " LEFT JOIN tram.dmLoaiCotAngtenEntity" +
                        " WHERE tram.id = :id")
        TramEntity findByIdWithGraphDanhMuc(Long id);

        // user
        @EntityGraph(attributePaths = { "dmLoaiThietBiRanEntityList", "dmPhongDaiEntity", "dmToEntity",
                        "dmTinhEntity", "dmHuyenEntity", "dmXaEntity", "dmTramKhuVucEntity", "dmLoaiCshtEntity",
                        "dmLoaiTramEntity", "dmLoaiCotAngtenEntity" })
        @Query("select distinct c " +
                        "from TramEntity c " +
                        "left join c.dmPhongDaiEntity d " +
                        "left join c.dmToEntity e " +
                        "left join c.dmTinhEntity f " +
                        "left join c.dmHuyenEntity g " +
                        "left join c.dmXaEntity h " +
                        "left join c.dmTramKhuVucEntity i " +
                        "left join c.dmLoaiCshtEntity j " +
                        "left join c.dmLoaiTramEntity k " +
                        "left join c.dmLoaiCotAngtenEntity l " +
                        "left join c.dmLoaiThietBiRanEntityList m " +
                        // "left join c.hopDongEntityList " +
                        "where c.dmPhongDaiEntity.id in :ids " +
                        "and ( :search is null or ( " +
                        "c.maTram like %:search% " +
                        "or c.maTramErp like %:search% " +
                        "or c.ten like %:search% " +
                        "))" +
                        "and (:tinh is null or f.id = :tinh) " +
                        "and (:huyen is null or g.id = :huyen) " +
                        "and (:xa is null or h.id = :xa) " +
                        "and (:phongDai is null or d.id = :phongDai) " +
                        "and (:to is null or e.id = :to) " +
                        "and (:trangThaiHoatDong is null or c.trangThaiHoatDong = :trangThaiHoatDong) " +
                        "and (:trangThaiPhatSong is null or c.daPhatSong = :trangThaiPhatSong) " +
                        "and (:loaiCsht is null or j.id = :loaiCsht)")
        Page<TramEntity> findAllByPhongDaiIdAndFetchAllWhereSearch(
                        @Param("search") String search,
                        @Param("tinh") Integer tinh,
                        @Param("huyen") Integer huyen,
                        @Param("xa") Integer xa,
                        @Param("phongDai") Integer phongDai,
                        @Param("to") Integer to,
                        @Param("trangThaiHoatDong") TrangThaiTram trangThaiHoatDong,
                        @Param("loaiCsht") Integer loaiCsht,
                        @Param("ids") List<Integer> ids,
                        @Param("trangThaiPhatSong") Boolean trangThaiPhatSong,
                        Pageable pageable);

        @EntityGraph(attributePaths = { "dmLoaiThietBiRanEntityList", "dmPhongDaiEntity", "dmToEntity",
                        "dmTinhEntity", "dmHuyenEntity", "dmXaEntity", "dmTramKhuVucEntity", "dmLoaiCshtEntity",
                        "dmLoaiTramEntity", "dmLoaiCotAngtenEntity" })
        @Query("select distinct a " +
                        "from TramEntity a " +
                        "where a.phongDaiId in :ids " +
                        "and (:search is null or ( " +
                        "a.maTram like %:search% " +
                        "or a.maTramErp like %:search% " +
                        "or a.ten like %:search%)) " +
                        "and (:trangThaiHoatDong is null or a.trangThaiHoatDong = :trangThaiHoatDong) " +
                        "and (:trangThaiPhatSong is null or a.daPhatSong = :trangThaiPhatSong) " +
                        "and (:phongDai is null or a.phongDaiId = :phongDai)")
        Page<TramEntity> findAllBySearch(
                        @Param("search") String search,
                        @Param("phongDai") Integer phongDai,
                        @Param("trangThaiHoatDong") TrangThaiTram trangThaiHoatDong,
                        @Param("ids") List<Integer> ids,
                        @Param("trangThaiPhatSong") Boolean trangThaiPhatSong,
                        Pageable pageable);

        @Query("select a from TramEntity a where (a.maTram = :maTram or (:maTramErp is not null and a.maTramErp = :maTramErp)) and (:id is null or a.id <> :id)")
        List<TramEntity> findAllByMaTramAndMaTramErp(@Param("id") Long id, @Param("maTram") String maTram,
                        @Param("maTramErp") String maTramErp);

        @Query("select distinct a from TramEntity a where a.maTramErp =:maTramErp ")
        List<TramEntity> findByMaTramErp(String maTramErp);

        @Query("select distinct a from TramEntity a where a.maTram =:maTram ")
        List<TramEntity> findByMaTram(String maTram);

        @Query("select distinct a from TramEntity a where a.maDauTuXayDung =:maDauTuXayDung ")
        TramEntity findByMaDauTuXayDung(@Param("maDauTuXayDung") String maDauTuXayDung);

        @Query("select count(distinct a) " +
                        "from TramEntity a " +
                        "where a.phongDaiId in :ids")
        long countTram(@Param("ids") List<Integer> ids);

        @Query("select count(distinct a) " +
                        "from TramEntity a " +
                        "where a.trangThaiHoatDong = :trangThaiHoatDong " +
                        "and a.phongDaiId in :ids")
        long countTramHoatDong(@Param("trangThaiHoatDong") TrangThaiTram trangThaiHoatDong,
                        @Param("ids") List<Integer> ids);

        @Query("select count(distinct a) " +
                        "from TramEntity a " +
                        "where a.phongDaiId in :ids " +
                        "and a.trangThaiHoatDong = :trangThaiHoatDong " +
                        "and a.daPhatSong = true ")
        long countTramPhatSong(@Param("trangThaiHoatDong") TrangThaiTram trangThaiHoatDong,
                        @Param("ids") List<Integer> ids);

        @Query("select count(distinct a) " +
                        "from TramEntity a " +
                        "where a.phongDaiId in :ids " +
                        "and a.trangThaiHoatDong = :trangThaiHoatDong " +
                        "and a.daPhatSong = false")
        long countTramChuaPhatSong(@Param("trangThaiHoatDong") TrangThaiTram trangThaiHoatDong,
                        @Param("ids") List<Integer> ids);

        // admin
        @EntityGraph(attributePaths = { "dmLoaiThietBiRanEntityList", "dmPhongDaiEntity", "dmToEntity",
                        "dmTinhEntity", "dmHuyenEntity", "dmXaEntity", "dmTramKhuVucEntity", "dmLoaiCshtEntity",
                        "dmLoaiTramEntity", "dmLoaiCotAngtenEntity" })
        @Query("select distinct c " +
                        "from TramEntity c " +
                        "left join c.dmPhongDaiEntity d " +
                        "left join c.dmToEntity e " +
                        "left join c.dmTinhEntity f " +
                        "left join c.dmHuyenEntity g " +
                        "left join c.dmXaEntity h " +
                        "left join c.dmTramKhuVucEntity i " +
                        "left join c.dmLoaiCshtEntity j " +
                        "left join c.dmLoaiTramEntity k " +
                        "left join c.dmLoaiCotAngtenEntity l " +
                        "left join c.dmLoaiThietBiRanEntityList m " +
                        // "left join c.hopDongEntityList " +
                        "where ( :search is null or ( " +
                        "c.maTram like %:search% " +
                        "or c.maTramErp like %:search% " +
                        "or c.ten like %:search% " +
                        "))" +
                        "and (:tinh is null or f.id = :tinh) " +
                        "and (:huyen is null or g.id = :huyen) " +
                        "and (:xa is null or h.id = :xa) " +
                        "and (:phongDai is null or d.id = :phongDai) " +
                        "and (:to is null or e.id = :to) " +
                        "and (:trangThaiHoatDong is null or c.trangThaiHoatDong = :trangThaiHoatDong) " +
                        "and (:trangThaiPhatSong is null or c.daPhatSong = :trangThaiPhatSong) " +
                        "and (:loaiCsht is null or j.id = :loaiCsht)")
        Page<TramEntity> adminFindAllByPhongDaiIdAndFetchAllWhereSearch(
                        @Param("search") String search,
                        @Param("tinh") Integer tinh,
                        @Param("huyen") Integer huyen,
                        @Param("xa") Integer xa,
                        @Param("phongDai") Integer phongDai,
                        @Param("to") Integer to,
                        @Param("trangThaiHoatDong") TrangThaiTram trangThaiHoatDong,
                        @Param("loaiCsht") Integer loaiCsht,
                        @Param("trangThaiPhatSong") Boolean trangThaiPhatSong,
                        Pageable pageable);

        @EntityGraph(attributePaths = { "dmLoaiThietBiRanEntityList", "dmPhongDaiEntity", "dmToEntity",
                        "dmTinhEntity", "dmHuyenEntity", "dmXaEntity", "dmTramKhuVucEntity", "dmLoaiCshtEntity",
                        "dmLoaiTramEntity", "dmLoaiCotAngtenEntity" })
        @Query("select distinct a " +
                        "from TramEntity a " +
                        "where (:search is null or ( " +
                        "a.maTram like %:search% " +
                        "or a.maTramErp like %:search% " +
                        "or a.ten like %:search%)) " +
                        "and (:trangThaiHoatDong is null or a.trangThaiHoatDong = :trangThaiHoatDong) " +
                        "and (:trangThaiPhatSong is null or a.daPhatSong = :trangThaiPhatSong) " +
                        "and (:phongDai is null or a.phongDaiId = :phongDai)")
        Page<TramEntity> adminFindAllBySearch(
                        @Param("search") String search,
                        @Param("phongDai") Integer phongDai,
                        @Param("trangThaiHoatDong") TrangThaiTram trangThaiHoatDong,
                        @Param("trangThaiPhatSong") Boolean trangThaiPhatSong,
                        Pageable pageable);

        @Query("select count(distinct a) " +
                        "from TramEntity a ")
        long adminCountTram();

        @Query("select count(distinct a) " +
                        "from TramEntity a " +
                        "where a.trangThaiHoatDong = :trangThaiHoatDong ")
        long adminCountTramHoatDong(@Param("trangThaiHoatDong") TrangThaiTram trangThaiHoatDong);

        @Query("select count(distinct a) " +
                        "from TramEntity a " +
                        "where a.trangThaiHoatDong = :trangThaiHoatDong " +
                        "and a.daPhatSong = true ")
        long adminCountTramPhatSong(@Param("trangThaiHoatDong") TrangThaiTram trangThaiHoatDong);

        @Query("select count(distinct a) " +
                        "from TramEntity a " +
                        "where a.trangThaiHoatDong = :trangThaiHoatDong " +
                        "and a.daPhatSong = false ")
        long adminCountTramChuaPhatSong(@Param("trangThaiHoatDong") TrangThaiTram trangThaiHoatDong);

        @Query("select count(distinct a) from TramEntity a where a.loaiCotAngtenId =:loaiCotAngtenId")
        long countByLoaiCotAngtenId(@Param("loaiCotAngtenId") Integer loaiCotAngtenId);

        @Query("select count(distinct a) from TramEntity a where a.khuVucId =:id")
        long countByKhuVucId(@Param("id") Integer id);

        @Query("select count(distinct a) from TramEntity a where a.loaiCshtId =:id")
        long countByLoaiCshtId(@Param("id") Integer id);

        @Query("select count(distinct a) from TramEntity a where a.loaiTramId =:id")
        long countByLoaiTramId(@Param("id") Integer id);

        @Query("select count(distinct a) from TramEntity a where a.phongDaiId =:id")
        long countByPhongDaiId(@Param("id") Integer id);

        @Query("select count(distinct a) from TramEntity a where a.toId =:id")
        long countByToId(@Param("id") Integer id);

        @Query("select count(distinct a) from TramEntity a where a.tinhId =:id")
        long countByTinhId(@Param("id") Integer id);

        @Query("select count(distinct a) from TramEntity a where a.huyenId =:id")
        long countByHuyenId(@Param("id") Integer id);

        @Query("select count(distinct a) from TramEntity a where a.xaId =:id")
        long countByXaId(@Param("id") Integer id);

        /*
         * @Modifying
         * 
         * @Transactional
         * 
         * @Query("delete from TramEntity where id =:id")
         * void addPrefixToFirstName(Long id);
         */
}