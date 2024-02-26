package com.contract.danhmuc.doituongkyhopdong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contract.danhmuc.doituongkyhopdong.entity.DmDoiTuongKyHopDongEntity;

public interface DmDoiTuongKyHopDongRepository extends JpaRepository<DmDoiTuongKyHopDongEntity, Integer> {
    List<DmDoiTuongKyHopDongEntity> findAllByTen(String ten);
}
