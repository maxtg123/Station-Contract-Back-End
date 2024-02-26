package com.contract.hopdong.lichsu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.contract.authentication.component.KiemTraQuyenModuleHopDong;
import com.contract.hopdong.lichsu.model.HopDongLichSuModel;
import com.contract.hopdong.lichsu.repository.HopDongLichSuRepository;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Service
public class HopDongLichSuService {
  @Autowired
  private HopDongLichSuRepository hopDongLichSuRepository;
  @Autowired
  private NguoiDungService nguoiDungService;
  @Autowired
  private KiemTraQuyenModuleHopDong kiemTraQuyenModuleHopDong;

  public HopDongLichSuModel create(String changeLog, Long hopDongId) {
    HopDongLichSuModel toSave = new HopDongLichSuModel();
    toSave.setHopDongId(hopDongId);
    toSave.setNguoiDungId(nguoiDungService.getNguoiDung().getId());
    toSave.setChangeLog(changeLog);
    toSave.setVersion("0.0.0");

    HopDongLichSuModel saved = hopDongLichSuRepository.save(toSave);

    return saved;
  }

  public Page<HopDongLichSuModel> findAll(Integer page, Integer size, Long hopDongId) {
    List<Integer> pdIds = null;
    if (!nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
      pdIds = kiemTraQuyenModuleHopDong.layDanhSachIdPhongDaiDuocQuyenXem();
    }

    Pageable pageable = null;
    if (page != null && size != null) {
      pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    } else {
      pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("createdAt").descending());
    }

    Page<HopDongLichSuModel> data = hopDongLichSuRepository.findAll(pdIds, hopDongId, pageable);
    return data;
  }

}
