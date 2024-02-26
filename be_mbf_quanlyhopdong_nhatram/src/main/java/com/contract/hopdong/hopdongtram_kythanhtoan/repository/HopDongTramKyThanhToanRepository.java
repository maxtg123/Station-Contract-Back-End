package com.contract.hopdong.hopdongtram_kythanhtoan.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;

@Repository
public interface HopDongTramKyThanhToanRepository
        extends JpaRepository<HopDongTramKyThanhToanEntity, Long> {

    @Transactional
    void deleteAllByHopDongTramId(Long id);

    @EntityGraph(attributePaths = { "nguoiDungEntity" })
    @Query("select a from HopDongTramKyThanhToanEntity a where a.hopDongTramId = :hdTramId order by a.tuNgay")
    Set<HopDongTramKyThanhToanEntity> findAllByHopDongTramId(@Param("hdTramId") Long hdTramId);
}
