package com.contract.hopdong.hopdongdamphan_nguoinhan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.hopdong.hopdongdamphan_nguoinhan.entity.HopDongDamPhanNguoiNhanEntity;
import com.contract.hopdong.hopdongdamphan_nguoinhan.repository.HopDongDamPhanNguoiNhanRepository;

@Service
public class HopDongDamPhanNguoiNhanService {
  @Autowired
  private HopDongDamPhanNguoiNhanRepository hopDongDamPhanNguoiNhanRepository;

  public List<HopDongDamPhanNguoiNhanEntity> saveAll(List<HopDongDamPhanNguoiNhanEntity> entities) {
    return hopDongDamPhanNguoiNhanRepository.saveAll(entities);
  }

  public List<HopDongDamPhanNguoiNhanEntity> findAllByHopDongDamPhanId(Long damPhanId) {
    return hopDongDamPhanNguoiNhanRepository.findAllByHopDongDamPhanId(damPhanId);
  }
}
