package com.contract.hopdong.hopdongpheduyet_tientrinh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongpheduyet_tientrinh.entity.HopDongPheDuyetTienTrinhEntity;

@Repository
public interface HopDongPheDuyetTienTrinhRepository extends JpaRepository<HopDongPheDuyetTienTrinhEntity, Long> {

}
