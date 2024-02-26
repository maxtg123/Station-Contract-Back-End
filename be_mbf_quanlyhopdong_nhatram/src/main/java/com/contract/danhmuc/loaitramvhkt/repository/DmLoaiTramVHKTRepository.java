package com.contract.danhmuc.loaitramvhkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.danhmuc.loaitramvhkt.entity.DmLoaiTramVHKTEntity;

@Repository
public interface DmLoaiTramVHKTRepository extends JpaRepository<DmLoaiTramVHKTEntity, Integer> {
}
