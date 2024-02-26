package com.contract.hopdong.lichsu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.hopdong.lichsu.model.HopDongLichSuModel;

@Repository
public interface HopDongLichSuRepository extends JpaRepository<HopDongLichSuModel, Long> {

        @EntityGraph(attributePaths = { "nguoiDungEntity", "hopDongEntity", "hopDongEntity.hopDongTramEntities",
                        "hopDongEntity.hopDongTramEntities.tramEntity" })
        @Query("select distinct lichSu from HopDongLichSuModel lichSu" + " left join lichSu.hopDongEntity hopDong"
                        + " left join hopDong.hopDongTramEntities hopDongTram"
                        + " left join hopDongTram.tramEntity tram"
                        + " where (:hopDongId is null or lichSu.hopDongId =:hopDongId)"
                        + " and (:pdIds is null or tram.phongDaiId in :pdIds)")
        Page<HopDongLichSuModel> findAll(@Param("pdIds") List<Integer> pdIds, @Param("hopDongId") Long hopDongId,
                        Pageable pageable);
}
