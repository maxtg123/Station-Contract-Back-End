package com.contract.hopdong.hopdongphuluc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.base.service.BaseService;
import com.contract.common.exception.InvalidDataException;
import com.contract.hopdong.hopdongphuluc.entity.HopDongPhuLucEntity;
import com.contract.hopdong.hopdongphuluc.model.HopDongPhuLucModel;
import com.contract.hopdong.hopdongphuluc.repository.HopDongPhuLucRepository;

@Service
public class HopDongPhuLucService extends BaseService {
  @Autowired
  private HopDongPhuLucRepository hopDongPhuLucRepository;

  public HopDongPhuLucModel create(HopDongPhuLucModel hopDongPhuLucModel) {
    return save(HopDongPhuLucEntity.fromModel(null, hopDongPhuLucModel));
  }

  private HopDongPhuLucModel save(HopDongPhuLucEntity entity) {
    try {
      HopDongPhuLucEntity saved = hopDongPhuLucRepository.save(entity);
      return HopDongPhuLucModel.fromEntity(saved, false);
    } catch (Exception e) {
      System.out.println(e);
      throw new InvalidDataException();
    }
  }
}
