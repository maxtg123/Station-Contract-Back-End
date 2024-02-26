package com.contract.old.hopdong_files.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.old.hopdong_files.entity.OldHopDongFileEntity;

@Repository
public interface OldHopDongFileRepository extends JpaRepository<OldHopDongFileEntity, Long> {

  List<OldHopDongFileEntity> findAllBySoHopDong(String soHopDong);
}
