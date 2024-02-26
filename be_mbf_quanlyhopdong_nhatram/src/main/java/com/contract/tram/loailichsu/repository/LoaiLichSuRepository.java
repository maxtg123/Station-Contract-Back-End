package com.contract.tram.loailichsu.repository;

import com.contract.tram.loailichsu.entity.LoaiLichSuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiLichSuRepository extends JpaRepository<LoaiLichSuEntity, Integer> {
}
