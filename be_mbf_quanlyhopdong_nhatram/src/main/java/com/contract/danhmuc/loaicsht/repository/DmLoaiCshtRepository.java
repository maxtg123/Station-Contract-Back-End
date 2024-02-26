package com.contract.danhmuc.loaicsht.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contract.danhmuc.loaicsht.entity.DmLoaiCshtEntity;

public interface DmLoaiCshtRepository extends JpaRepository<DmLoaiCshtEntity, Integer> {
    DmLoaiCshtEntity findByMa(String ma);

    DmLoaiCshtEntity findByTen(String ten);
}
