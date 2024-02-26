package com.contract.danhmuc.xa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contract.danhmuc.xa.entity.DmXaEntity;

public interface DmXaRepository extends JpaRepository<DmXaEntity, Integer> {
    @Query(value = "From DmXaEntity where ten=:tenXa and huyenId=:huyenId")
    DmXaEntity kiemTraXaTonTai(String tenXa, int huyenId);

    DmXaEntity findByTen(String ten);

    List<DmXaEntity> findByhuyenId(int huyenId);
}
