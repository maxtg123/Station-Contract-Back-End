package com.contract.danhmuc.huyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contract.danhmuc.huyen.entity.DmHuyenEntity;

public interface DmHuyenRepository extends JpaRepository<DmHuyenEntity, Integer> {
    @Query(value = "Select id from DmHuyenEntity where ten=:tenHuyen")
    int findIdByTen(String tenHuyen);

    @Query(value = "Select count(id) from DmHuyenEntity where ten=:tenHuyen")
    int countIdByTen(String tenHuyen);

    @Query(value = "Select id from DmHuyenEntity where ten=:tenHuyen and tinhId=:tinhId")
    int findIdByTenAndTinhId(String tenHuyen, int tinhId);

    DmHuyenEntity findByMa(String maQuanHuyen);

    List<DmHuyenEntity> findBytinhId(int tinhId);

    DmHuyenEntity findByTen(String ten);
}
