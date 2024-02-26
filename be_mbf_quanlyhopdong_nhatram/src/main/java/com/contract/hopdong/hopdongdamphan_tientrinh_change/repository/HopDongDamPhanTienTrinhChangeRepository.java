package com.contract.hopdong.hopdongdamphan_tientrinh_change.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongdamphan_tientrinh_change.entity.HopDongDamPhanTienTrinhChangeEntity;

@Repository
public interface HopDongDamPhanTienTrinhChangeRepository
    extends JpaRepository<HopDongDamPhanTienTrinhChangeEntity, Long> {

}
