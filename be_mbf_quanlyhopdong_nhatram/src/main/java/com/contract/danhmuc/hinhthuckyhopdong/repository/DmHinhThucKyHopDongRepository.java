package com.contract.danhmuc.hinhthuckyhopdong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contract.danhmuc.hinhthuckyhopdong.entity.DmHinhThucKyHopDongEntity;

public interface DmHinhThucKyHopDongRepository extends JpaRepository<DmHinhThucKyHopDongEntity, Integer> {
    List<DmHinhThucKyHopDongEntity> findAllByTen(String ten);
}
