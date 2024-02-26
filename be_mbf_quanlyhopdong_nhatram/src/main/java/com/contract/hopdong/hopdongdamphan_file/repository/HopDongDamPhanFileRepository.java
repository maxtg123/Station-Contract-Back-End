package com.contract.hopdong.hopdongdamphan_file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongdamphan_file.entity.HopDongDamPhanFileEntity;

@Repository
public interface HopDongDamPhanFileRepository extends JpaRepository<HopDongDamPhanFileEntity, Long> {

}
