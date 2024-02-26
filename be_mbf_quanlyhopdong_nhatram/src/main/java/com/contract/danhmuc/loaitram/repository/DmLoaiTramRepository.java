package com.contract.danhmuc.loaitram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contract.danhmuc.loaitram.entity.DmLoaiTramEntity;

public interface DmLoaiTramRepository extends JpaRepository<DmLoaiTramEntity, Integer> {
    DmLoaiTramEntity findByTen(String ten);
}
