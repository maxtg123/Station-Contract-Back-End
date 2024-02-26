package com.contract.old.hopdong_files.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.contract.old.hopdong_files.entity.OldHopDongFileEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class OldHopDongFileModel {
  public static OldHopDongFileModel fromEntity(OldHopDongFileEntity entity, boolean containChild) {
    OldHopDongFileModel model = new OldHopDongFileModel();
    model.setId(entity.getId());
    model.setSoHopDong(entity.getSoHopDong());
    model.setFileList(entity.getFileList());
    model.setFileId(entity.getFileId());
    model.setFilePath(entity.getFilePath());

    model.setPath("uploads/OLD_HOPDONG/HopDong" + entity.getFilePath());

    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "so_hop_dong")
  protected String soHopDong;

  @Column(name = "file_list")
  protected String fileList;

  @Column(name = "file_id")
  protected Long fileId;

  @Column(name = "file_path")
  protected String filePath;

  @Transient
  protected String path;

}
