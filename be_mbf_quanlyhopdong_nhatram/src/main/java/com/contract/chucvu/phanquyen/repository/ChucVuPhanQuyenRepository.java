package com.contract.chucvu.phanquyen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.chucvu.phanquyen.entity.ChucVuPhanQuyenEntity;

@Repository
public interface ChucVuPhanQuyenRepository extends JpaRepository<ChucVuPhanQuyenEntity, Long> {
    long deleteByPhongDaiChucVuId(Long id);
}
