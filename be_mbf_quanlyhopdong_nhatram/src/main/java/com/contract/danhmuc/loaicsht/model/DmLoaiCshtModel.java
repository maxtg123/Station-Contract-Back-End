package com.contract.danhmuc.loaicsht.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.contract.common.entity.AuditEntity;
import com.contract.danhmuc.loaicsht.entity.DmLoaiCshtEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class DmLoaiCshtModel extends AuditEntity<String> {

  public static DmLoaiCshtModel fromEntity(DmLoaiCshtEntity entity) {
    DmLoaiCshtModel model = new DmLoaiCshtModel();
    model.setId(entity.getId());
    model.setCreatedAt(entity.getCreatedAt());
    model.setCreatedBy(entity.getCreatedBy());
    model.setUpdatedAt(entity.getUpdatedAt());
    model.setUpdatedBy(entity.getUpdatedBy());
    // model.setDeletedAt(entity.getDeletedAt());

    model.setTen(entity.getTen());
    model.setMa(entity.getMa());
    model.setGhiChu(entity.getGhiChu());
    model.setMaDataSite(entity.getMaDataSite());
    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Integer id = 0;

  @Column(name = "ma_datasite", nullable = true)
  protected String maDataSite;

  @Column(name = "ten", nullable = false)
  @NotBlank
  @NotEmpty
  @NotNull(message = "Tên không được trống")
  protected String ten;

  @Column(name = "ma", nullable = true)
  protected String ma;

  @Column(name = "ghi_chu")
  protected String ghiChu;

  @Override
  public String toString() {
    return "DmLoaiCshtModel{" + "ten='" + ten + '\'' + ", ma=" + ma + ", ghi_chu=" + ghiChu + '}';
  }

}
