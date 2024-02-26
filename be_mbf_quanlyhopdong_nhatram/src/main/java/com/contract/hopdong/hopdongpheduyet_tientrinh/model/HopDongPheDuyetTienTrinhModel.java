package com.contract.hopdong.hopdongpheduyet_tientrinh.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Comment;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdongpheduyet.enums.TrangThaiPheDuyetEnum;
import com.contract.hopdong.hopdongpheduyet_tientrinh.entity.HopDongPheDuyetTienTrinhEntity;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongPheDuyetTienTrinhModel extends AuditEntity<String> {
  public static HopDongPheDuyetTienTrinhModel fromEntity(HopDongPheDuyetTienTrinhEntity entity, boolean containChild) {
    HopDongPheDuyetTienTrinhModel model = new HopDongPheDuyetTienTrinhModel();
    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());
    model.setId(entity.getId());

    model.setHopDongPheDuyetId(entity.getHopDongPheDuyetId());
    model.setGhiChu(entity.getGhiChu());
    model.setTrangThaiPheDuyet(entity.getTrangThaiPheDuyet());
    model.setNguoiDungId(entity.getNguoiDungId());
    model.setChangeLog(entity.getChangeLog());
    model.setChangeLogClob(entity.getChangeLogClob());

    if (containChild) {
      if (entity.getNguoiDungEntity() != null) {
        model.setNguoiDung(NguoiDungModel.fromEntity(entity.getNguoiDungEntity(), false));
      }
    }
    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "hop_dong_phe_duyet_id")
  protected Long hopDongPheDuyetId;

  @Column(name = "ghi_chu")
  protected String ghiChu;

  @Column(name = "trang_thai_phe_duyet")
  @Comment("0: CHO_PHE_DUYET, 1: PHE_DUYET, 2: TU_CHOI, 3: GUI_LAI")
  @Enumerated(EnumType.ORDINAL)
  protected TrangThaiPheDuyetEnum trangThaiPheDuyet;

  @Column(name = "nguoi_dung_id")
  protected Long nguoiDungId;

  @Column(name = "change_log", length = 3600)
  protected String changeLog;

  @Lob
  @Column(name = "change_log_clob", nullable = true, columnDefinition = "CLOB")
  protected String changeLogClob;

  @Transient
  protected NguoiDungModel nguoiDung;
}
