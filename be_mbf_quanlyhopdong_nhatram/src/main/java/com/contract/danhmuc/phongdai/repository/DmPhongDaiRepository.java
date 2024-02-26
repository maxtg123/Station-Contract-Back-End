package com.contract.danhmuc.phongdai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;

public interface DmPhongDaiRepository extends JpaRepository<DmPhongDaiEntity, Integer> {
    @Query(value = "select id from DmPhongDaiEntity where ten=:tenPhongDai")
    int findIdByTen(String tenPhongDai);

    @Query(value = "Select count(id) from DmPhongDaiEntity where ten=:tenPhongDai")
    int countIdByTen(String tenPhongDai);

    DmPhongDaiEntity findByTen(String ten);

    DmPhongDaiEntity findByTenVietTat(String tenVietTat);

    List<DmPhongDaiEntity> findAllByIdIn(List<Integer> ids);

    List<DmPhongDaiEntity> findAllByLoai(String loai);
}
