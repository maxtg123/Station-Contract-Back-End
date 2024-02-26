package com.contract.danhmuc.loaiphongmay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.danhmuc.loaiphongmay.entity.DmLoaiPhongMayEntity;

@Repository
public interface DmLoaiPhongMayRepository extends JpaRepository<DmLoaiPhongMayEntity, Integer> {
}