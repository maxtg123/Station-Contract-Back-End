package com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.entity.HopDongDamPhanTienTrinhXetDuyetEntity;

@Repository
public interface HopDongDamPhanTienTrinhXetDuyetRepository
    extends JpaRepository<HopDongDamPhanTienTrinhXetDuyetEntity, Long> {

}
