package com.contract.danhmuc.loaiphongmayphatdien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.danhmuc.loaiphongmayphatdien.entity.DmLoaiPhongMayPhatDienEntity;

@Repository
public interface DmLoaiPhongMayPhatDienRepository extends JpaRepository<DmLoaiPhongMayPhatDienEntity, Integer> {
}
