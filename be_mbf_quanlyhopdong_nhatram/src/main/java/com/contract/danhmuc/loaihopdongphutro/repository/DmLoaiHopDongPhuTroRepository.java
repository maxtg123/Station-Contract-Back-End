package com.contract.danhmuc.loaihopdongphutro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.danhmuc.loaihopdongphutro.entity.DmLoaiHopDongPhuTroEntity;

@Repository
public interface DmLoaiHopDongPhuTroRepository extends JpaRepository<DmLoaiHopDongPhuTroEntity, Integer> {
    DmLoaiHopDongPhuTroEntity findByTen(String ten);
}