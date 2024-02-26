package com.contract.old.hopdong_files.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.old.hopdong_files.entity.OldHopDongFileEntity;
import com.contract.old.hopdong_files.model.OldHopDongFileModel;
import com.contract.old.hopdong_files.repository.OldHopDongFileRepository;

@Service
public class OldHopDongFileService {
  @Autowired
  private OldHopDongFileRepository oldHopDongFileRepository;

  public List<OldHopDongFileModel> findAllBySoHopDong(String soHopDong) {
    List<OldHopDongFileEntity> entities = oldHopDongFileRepository.findAllBySoHopDong(soHopDong);
    if (entities != null && entities.size() > 0) {
      return entities.stream().map(_entity -> OldHopDongFileModel.fromEntity(_entity, false))
          .collect(Collectors.toList());
    }
    return null;
  }
}
