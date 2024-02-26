package com.contract.hopdong.hopdongfile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongfile.entity.HopDongFileEntity;

@Repository
public interface HopDongFileRepository extends JpaRepository<HopDongFileEntity, Long> {
  List<HopDongFileEntity> findAllByHopDongId(Long hopDongId);

  void deleteAllByHopDongTramId(Long hopDongTramId);
}
