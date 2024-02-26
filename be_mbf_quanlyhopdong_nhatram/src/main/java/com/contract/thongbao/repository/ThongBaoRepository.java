package com.contract.thongbao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.thongbao.entity.ThongBaoEntity;
import com.contract.thongbao.model.TrangThaiThongBao;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBaoEntity, Long> {
    @EntityGraph(attributePaths = { "nguoiGuiEntity", "nguoiNhanEntity" })
    @Query("select a from ThongBaoEntity a where (:nguoiNhanId is null or a.nguoiNhanId = :nguoiNhanId) and (:nguoiGuiId is null or a.nguoiGuiId = :nguoiGuiId) and (:module is null or a.module = :module) and (:action is null or a.action = :action) and (:trangThai is null or a.trangThai = :trangThai)")
    Page<ThongBaoEntity> findAllWithNguoiDung(@Param("nguoiNhanId") Long nguoiNhanId,
            @Param("nguoiGuiId") Long nguoiGuiId,
            @Param("module") String module, @Param("action") String action,
            @Param("trangThai") TrangThaiThongBao trangThai, Pageable pageable);
}
