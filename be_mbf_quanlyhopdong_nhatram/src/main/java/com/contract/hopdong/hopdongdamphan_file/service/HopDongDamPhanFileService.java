package com.contract.hopdong.hopdongdamphan_file.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.contract.common.exception.InvalidDataException;
import com.contract.common.util.FileUtil;
import com.contract.hopdong.hopdongdamphan_file.entity.HopDongDamPhanFileEntity;
import com.contract.hopdong.hopdongdamphan_file.model.HopDongDamPhanFileModel;
import com.contract.hopdong.hopdongdamphan_file.repository.HopDongDamPhanFileRepository;

@Service
public class HopDongDamPhanFileService {
  @Autowired
  private HopDongDamPhanFileRepository hopDongDamPhanFileRepository;

  private final String rootFolder = Paths.get("uploads", "DAM_PHAN").toFile().getAbsolutePath();

  public void addFiles(MultipartFile[] files, Long hopDongId, Long damPhanId, Long tienTrinhId) throws Exception {
    if (files != null) {
      for (int i = 0; i < files.length; i++) {
        HopDongDamPhanFileModel model = new HopDongDamPhanFileModel();
        model.setTen(files[i].getOriginalFilename());
        model.setHopDongDamPhanId(damPhanId);
        model.setHopDongDamPhanTienTrinhId(tienTrinhId);

        HopDongDamPhanFileEntity savedEntity = hopDongDamPhanFileRepository
            .save(HopDongDamPhanFileEntity.fromModel(model));
        saveFile(savedEntity, files[i], hopDongId);
      }
    }
  }

  private void saveFile(HopDongDamPhanFileEntity fileEntity, MultipartFile file, Long hopDongId) throws Exception {
    if (file == null || fileEntity == null) {
      throw new InvalidDataException();
    }
    Path path = Paths.get(rootFolder, fileEntity.getHopDongDamPhanId().toString(),
        fileEntity.getHopDongDamPhanTienTrinhId().toString(), fileEntity.getId().toString());
    FileUtil.saveFile(path.toFile().getAbsolutePath(), file.getOriginalFilename(), file);
  }
}
