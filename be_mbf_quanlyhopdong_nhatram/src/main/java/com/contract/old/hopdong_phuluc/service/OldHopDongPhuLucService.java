package com.contract.old.hopdong_phuluc.service;

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
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.hopdong.hopdongphuluc.entity.HopDongPhuLucEntity;
import com.contract.hopdong.hopdongphuluc.enums.TinhTrangPhuLucEnum;
import com.contract.hopdong.hopdongphuluc.model.HopDongPhuLucModel;
import com.contract.hopdong.hopdongphuluc.repository.HopDongPhuLucRepository;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import com.contract.old.hopdong_phuluc.entity.OldHopDongPhuLucEntity;
import com.contract.old.hopdong_phuluc.repository.OldHopDongPhuLucRepository;
import com.contract.process.enums.ProcessActionEnum;
import com.contract.process.enums.ProcessModuleEnum;
import com.contract.process.model.ProcessModel;
import com.contract.process.service.ProcessService;

@Service
public class OldHopDongPhuLucService {
  @Autowired
  private OldHopDongPhuLucRepository oldHopDongPhuLucRepository;
  @Autowired
  private NguoiDungService nguoiDungService;
  @Autowired
  private ProcessService processService;
  @Autowired
  private HopDongRepository hopDongRepository;
  @Autowired
  private HopDongPhuLucRepository hopDongPhuLucRepository;

  public Long syncPhuLuc() {

    NguoiDungEntity nguoiDungEntity = nguoiDungService.getNguoiDung();
    if (nguoiDungEntity == null) {
      throw new UnauthorizedException();
    }

    ProcessModel processModel = new ProcessModel();
    processModel.setUserId(nguoiDungEntity.getId());
    processModel.setModule(ProcessModuleEnum.HOP_DONG.name());
    processModel.setAction(ProcessActionEnum.DONG_BO_PHU_LUC_TU_CHUONG_TRINH_CU.name());
    processModel.setTongSo((long) 0);
    processModel = processService.create(processModel);
    final ProcessModel savedProcess = processModel;

    CompletableFuture.runAsync(() -> backgroundSyncPhuLuc(savedProcess,
        nguoiDungEntity.getEmail(), nguoiDungEntity.getMatKhau()));

    return processModel.getId();
  }

  private void setUserNewThread(String email, String matKhau) {
    UserDetails userDetails = new org.springframework.security.core.userdetails.User(email, matKhau, new ArrayList<>());
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

  private void backgroundSyncPhuLuc(ProcessModel savedProcess, String userName, String password) {
    setUserNewThread(userName, password);

    List<OldHopDongPhuLucEntity> oldHopDongPhuLucEntities = oldHopDongPhuLucRepository.findAll();
    if (oldHopDongPhuLucEntities == null || oldHopDongPhuLucEntities.size() == 0) {
      savedProcess.setTongSo((long) 0);
      savedProcess.setHoanThanh((long) 0);
      savedProcess.setSoLuongLoi((long) 0);
      savedProcess.setKetThuc(true);
      processService.update(savedProcess);
      return;
    }

    try {
      oldHopDongPhuLucEntities.forEach(_oldHdPL -> {
        HopDongEntity hopDongEntity = hopDongRepository.findBySoHopDongWithoutFetch(_oldHdPL.getSoHopDong());
        if (hopDongEntity != null && _oldHdPL.getSoPhuLuc() != null) {
          HopDongPhuLucEntity hopDongPhuLucEntity = hopDongPhuLucRepository.findBySoPhuLuc(_oldHdPL.getSoPhuLuc());
          HopDongPhuLucModel model = new HopDongPhuLucModel();
          model.setHopDongId(hopDongEntity.getId());
          model.setSoPhuLuc(_oldHdPL.getSoPhuLuc());
          model.setNgayKy(_oldHdPL.getNgayKy());
          model.setNgayHieuLuc(_oldHdPL.getNgayHieuLuc());
          model.setNgayKetThuc(_oldHdPL.getNgayKetThuc());
          model.setGhiChu(_oldHdPL.getGhiChu());
          model.setTinhTrangPhuLuc(
              _oldHdPL.getIsactive() == 2 ? TinhTrangPhuLucEnum.HOAT_DONG : TinhTrangPhuLucEnum.NGUNG_HOAT_DONG);

          hopDongPhuLucRepository.save(HopDongPhuLucEntity.fromModel(hopDongPhuLucEntity, model));
        }
      });
    } catch (Exception e) {
      savedProcess.setSoLuongLoi((long) 1);
      savedProcess.setKetThuc(true);
      processService.update(savedProcess);
    }

    savedProcess.setKetThuc(true);
    processService.update(savedProcess);
  }
}
