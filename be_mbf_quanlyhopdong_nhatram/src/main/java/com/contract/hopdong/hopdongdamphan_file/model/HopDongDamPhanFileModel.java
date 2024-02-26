package com.contract.hopdong.hopdongdamphan_file.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdongdamphan_file.entity.HopDongDamPhanFileEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongDamPhanFileModel extends AuditEntity<String> {

  public static HopDongDamPhanFileModel fromEntity(HopDongDamPhanFileEntity entity,
      boolean containChild) {
    HopDongDamPhanFileModel model = new HopDongDamPhanFileModel();
    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());
    model.setId(entity.getId());
    model.setTen(entity.getTen());

    model.setHopDongDamPhanId(entity.getHopDongDamPhanId());
    model.setHopDongDamPhanTienTrinhId(entity.getHopDongDamPhanTienTrinhId());

    model.setPath("uploads/" + "DAM_PHAN" + "/" + entity.getHopDongDamPhanId() +
        "/" + entity.getHopDongDamPhanTienTrinhId() + "/" + entity.getId() + "/"
        + entity.getTen());
    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "ten")
  protected String ten;

  @Column(name = "hop_dong_dam_phan_id", nullable = false)
  protected Long hopDongDamPhanId;

  @Column(name = "hop_dong_dam_phan_tien_trinh_id", nullable = false)
  protected Long hopDongDamPhanTienTrinhId;

  @Transient
  protected String path;
}
