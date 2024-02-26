package com.contract.nguoidung.nguoidungkhuvuc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;

@Repository
public interface NguoiDungKhuVucRepository extends JpaRepository<NguoiDungKhuVucEntity, Long> {
    List<NguoiDungKhuVucEntity> findByNguoiDungId(Long id);

    long deleteByNguoiDungId(Long id);

    @Query("select count(distinct a) from NguoiDungKhuVucEntity a where a.phongDaiId =:id")
    long countByPhongDaiId(@Param("id") Integer id);

    @Query("select count(distinct a) from NguoiDungKhuVucEntity a where a.toId =:id")
    long countByToId(@Param("id") Integer id);
}
