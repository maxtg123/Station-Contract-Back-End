package com.contract.chucvu.chucvu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.chucvu.chucvu.entity.ChucVuEntity;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVuEntity, Long> {
        List<ChucVuEntity> findByTen(String ten);

        @Query("select distinct c from ChucVuEntity c")
        List<ChucVuEntity> findAllAndFetchUser();

        List<ChucVuEntity> findAllByTenAndPhongDaiId(String ten, Integer id);

        // @EntityGraph(attributePaths = { "nguoiDungKhuVucEntityList",
        // "listChucVuPhanQuyenEntity", "dmPhongDaiEntity" })
        @Query("select distinct c from ChucVuEntity c left join fetch c.nguoiDungKhuVucEntityList left join fetch c.listChucVuPhanQuyenEntity left join fetch c.dmPhongDaiEntity f where f.id in :ids")
        List<ChucVuEntity> findByDanhSachPhongDai(@Param("ids") List<Integer> ids);

        // @EntityGraph(attributePaths = { "nguoiDungKhuVucEntityList",
        // "nguoiDungKhuVucEntityList.nguoiDungEntity",
        // "listChucVuPhanQuyenEntity", "dmPhongDaiEntity" })
        @Query("select distinct c from ChucVuEntity c left join fetch c.nguoiDungKhuVucEntityList left join fetch c.listChucVuPhanQuyenEntity left join fetch c.dmPhongDaiEntity")
        List<ChucVuEntity> findAll();

        @Query("select count(distinct a) from ChucVuEntity a where a.phongDaiId =:id")
        long countByPhongDaiId(@Param("id") Integer id);
}
