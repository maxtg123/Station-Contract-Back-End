package com.contract.hopdong.hopdongtram_dungchung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;

@Repository
public interface HopDongTramDungChungRepository
        extends JpaRepository<HopDongTramDungChungEntity, Long> {
    void deleteAllByHopDongTramId(Long hopDongTramId);

    @Query("select count (distinct a) from HopDongTramDungChungEntity a where a.loaiHangMucCSHTId =:id")
    long countByLoaiHangMucCshtId(@Param("id") Integer id);
}
