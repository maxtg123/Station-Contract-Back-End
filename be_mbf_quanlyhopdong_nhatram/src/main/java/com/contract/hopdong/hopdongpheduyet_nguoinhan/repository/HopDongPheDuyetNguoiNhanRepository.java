package com.contract.hopdong.hopdongpheduyet_nguoinhan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongpheduyet_nguoinhan.entity.HopDongPheDuyetNguoiNhanEntity;

@Repository
public interface HopDongPheDuyetNguoiNhanRepository extends
    JpaRepository<HopDongPheDuyetNguoiNhanEntity, Long> {
}
