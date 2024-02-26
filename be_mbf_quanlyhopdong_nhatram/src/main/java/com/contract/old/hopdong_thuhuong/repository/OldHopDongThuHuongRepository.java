package com.contract.old.hopdong_thuhuong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contract.old.hopdong_thuhuong.model.OldHopDongThuHuongModel;

@Repository
public interface OldHopDongThuHuongRepository extends JpaRepository<OldHopDongThuHuongModel, Long> {

    @Query("select distinct  a from OldHopDongThuHuongModel a where trim(a.soHopDong)=:soHopDong")
    List<OldHopDongThuHuongModel> findAllBySoHopDong(@Param("soHopDong") String soHopDong);

    // @Query("select distinct a from OldHopDongThuHuongModel a"
    // + " where trim(a.soHopDong)=:soHopDong"
    // + " and a.donViQuanLy =:pdID ")
    // List<OldHopDongThuHuongModel>
    // findAllBySoHopDongAndPhongDai(@Param("soHopDong") String soHopDong,
    // @Param("phongDaiId") Integer pdID);

    @Query("select distinct a from OldHopDongThuHuongModel a" + " where trim(a.soHopDong)=:soHopDong"
            + " and a.donViQuanLy=:donViQuanLy")
    List<OldHopDongThuHuongModel> findAllBySoHopDongAndPhongDai(@Param("soHopDong") String soHopDong,
            @Param("donViQuanLy") Integer donViQuanLy);
}
