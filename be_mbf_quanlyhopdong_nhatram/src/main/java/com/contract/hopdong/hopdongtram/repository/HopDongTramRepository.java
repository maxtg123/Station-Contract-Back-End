package com.contract.hopdong.hopdongtram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;

@Repository
public interface HopDongTramRepository extends JpaRepository<HopDongTramEntity, Long> {

}
