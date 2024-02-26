package com.contract.hopdong.hopdongtram_phutro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.contract.hopdong.hopdongtram_phutro.entity.HopDongTramPhuTroEntity;

@Repository
public interface HopDongTramPhuTroRepository extends JpaRepository<HopDongTramPhuTroEntity, Long> {
  @Transactional
  void deleteAllByHopDongTramId(Long id);

  @Query("select count(distinct a) from HopDongTramPhuTroEntity a where a.dmPhuTroId =:id")
  long countByPhuTroId(@Param("id") Integer id);

  @EntityGraph(attributePaths = { "dmLoaiHopDongPhuTroEntity" })
  @Query("select distinct a from HopDongTramPhuTroEntity a where a.hopDongTramId =:hdTramId")
  List<HopDongTramPhuTroEntity> findAllByHopDongTramId(@Param("hdTramId") Long hdTramId);
}
