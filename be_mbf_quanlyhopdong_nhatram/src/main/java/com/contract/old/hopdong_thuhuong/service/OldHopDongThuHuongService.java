package com.contract.old.hopdong_thuhuong.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.contract.common.exception.UnauthorizedException;
import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdong.enums.TinhTrangHopDongEnum;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.hopdong.hopdongdoitac.entity.HopDongDoiTacEntity;
import com.contract.hopdong.hopdongdoitac.model.HopDongDoiTacModel;
import com.contract.hopdong.hopdongdoitac.repository.HopDongDoiTacRepository;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.old.hopdong_thuhuong.model.OldHopDongThuHuongModel;
import com.contract.old.hopdong_thuhuong.repository.OldHopDongThuHuongRepository;
import com.contract.process.enums.ProcessActionEnum;
import com.contract.process.enums.ProcessModuleEnum;
import com.contract.process.model.ProcessModel;
import com.contract.process.service.ProcessService;

@Service
public class OldHopDongThuHuongService {
  @Autowired
  private OldHopDongThuHuongRepository oldHopDongThuHuongRepository;
  @Autowired
  private ProcessService processService;
  @Autowired
  private HopDongRepository hopDongRepository;
  @Autowired
  private HopDongDoiTacRepository hopDongDoiTacRepository;
  @Autowired
  private NguoiDungService nguoiDungService;

  public Long syncThuHuong() {

    NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
    if (nguoiDungEntity == null) {
      throw new UnauthorizedException();
    }

    ProcessModel processModel = new ProcessModel();
    processModel.setUserId(nguoiDungEntity.getId());
    processModel.setModule(ProcessModuleEnum.HOP_DONG.name());
    processModel.setAction(ProcessActionEnum.DONG_BO_THU_HUONG_TU_CHUONG_TRINH_CU.name());
    processModel.setTongSo((long) 0);
    processModel = processService.create(processModel);
    final ProcessModel savedProcess = processModel;

    CompletableFuture.runAsync(() -> backgroundSync(savedProcess,
        nguoiDungEntity.getEmail(), nguoiDungEntity.getMatKhau()));

    return processModel.getId();
  }

  private void setUserNewThread(String email, String matKhau) {
    UserDetails userDetails = new org.springframework.security.core.userdetails.User(email, matKhau, new ArrayList<>());
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

  private void backgroundSync(ProcessModel savedProcess, String userName, String password) {
    if (savedProcess == null)
      return;
    setUserNewThread(userName, password);

    List<HopDongEntity> allHopDong = hopDongRepository.findAllHopDongAndDoiTacAndTramsPhongDai();
    if (allHopDong == null || allHopDong.size() == 0) {
      savedProcess.setTongSo((long) 0);
      savedProcess.setHoanThanh((long) 0);
      savedProcess.setSoLuongLoi((long) 0);
      savedProcess.setKetThuc(true);
      processService.update(savedProcess);
      return;
    }

    try {
      allHopDong.forEach(_hopDongEntity -> {
        if (_hopDongEntity.getHopDongTramEntities() != null && _hopDongEntity.getHopDongTramEntities().size() > 0) {
          HopDongTramEntity hdTram = new ArrayList<>(_hopDongEntity.getHopDongTramEntities()).get(0);
          String pdTenVietTat = hdTram.getTramEntity().getDmPhongDaiEntity().getTenVietTat();
          Integer donViQuanLy = convertPdTenVetTat2Old(pdTenVietTat);
          if (donViQuanLy != null) {
            List<OldHopDongThuHuongModel> oldHopDongThuHuongModels = oldHopDongThuHuongRepository
                .findAllBySoHopDongAndPhongDai(_hopDongEntity.getSoHopDong(), donViQuanLy);
            if (oldHopDongThuHuongModels != null && oldHopDongThuHuongModels.size() > 0) {
              OldHopDongThuHuongModel oldThuHuong = oldHopDongThuHuongModels.get(0);
              HopDongDoiTacEntity doiTacDb = hopDongDoiTacRepository.findByHopDongId(_hopDongEntity.getId());

              if (doiTacDb != null) {
                doiTacDb.setChuTaiKhoan(oldThuHuong.getChuTaiKhoan());
                doiTacDb.setSoTaiKhoan(oldThuHuong.getSoTaiKhoan());
                doiTacDb.setNganHangChiNhanh(oldThuHuong.getNganHang() + " - " +
                    oldThuHuong.getChiNhanh());
                hopDongDoiTacRepository.save(doiTacDb);
              } else {
                HopDongDoiTacModel doiTacModel = new HopDongDoiTacModel();
                doiTacModel.setHopDongId(_hopDongEntity.getId());
                doiTacModel.setChuTaiKhoan(oldThuHuong.getChuTaiKhoan());
                doiTacModel.setSoTaiKhoan(oldThuHuong.getSoTaiKhoan());
                doiTacModel.setNganHangChiNhanh(oldThuHuong.getNganHang() + " - " +
                    oldThuHuong.getChiNhanh());
                HopDongDoiTacEntity toSave = HopDongDoiTacEntity.fromModel(null, doiTacModel);
                hopDongDoiTacRepository.save(toSave);
              }

              // // Update tinh trang hop dong
              _hopDongEntity.setTinhTrangHopDong(convertOldToNewTinhTrangHD(oldThuHuong.getTrangThaiHopDongPhuLuc()));
              hopDongRepository.save(_hopDongEntity);
            }
          }
        }
      });
    } catch (Exception e) {
      System.out.println("syn thu huong error: " + e.getMessage());
      savedProcess.setSoLuongLoi((long) 1);
      savedProcess.setKetThuc(true);
      processService.update(savedProcess);
    }

    savedProcess.setKetThuc(true);
    processService.update(savedProcess);
  }

  private TinhTrangHopDongEnum convertOldToNewTinhTrangHD(Integer oldValue) {
    if (oldValue != null) {
      if (oldValue == 1) {
        return TinhTrangHopDongEnum.DI_DOI;
      }
      if (oldValue == 2) {
        return TinhTrangHopDongEnum.TAI_KY;
      }
      if (oldValue == 3) {
        return TinhTrangHopDongEnum.KY_MOI;
      }
      if (oldValue == 5) {
        return TinhTrangHopDongEnum.PHU_LUC;
      }
    }
    return null;
  }

  private Integer convertPdTenVetTat2Old(String pdTvt) {
    if (pdTvt == null)
      return null;
    if (pdTvt.equals("ĐVTĐ")) {
      return 2;
    }
    if (pdTvt.equals("ĐVTT")) {
      return 1;
    }
    if (pdTvt.equals("ĐVTĐN")) {
      return 9;
    }
    if (pdTvt.equals("ĐVTBD")) {
      return 8;
    }
    if (pdTvt.equals("ĐVTCT")) {
      return 10;
    }
    if (pdTvt.equals("ĐVTTG")) {
      return 11;
    }
    return null;
  }
}
