package com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Comment;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh.enums.TrangThaiDamPhanEnum;
import com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.entity.HopDongDamPhanTienTrinhXetDuyetEntity;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongDamPhanTienTrinhXetDuyetModel extends AuditEntity<String> {

  public static HopDongDamPhanTienTrinhXetDuyetModel fromEntity(HopDongDamPhanTienTrinhXetDuyetEntity entity,
      boolean containChild) {
    HopDongDamPhanTienTrinhXetDuyetModel model = new HopDongDamPhanTienTrinhXetDuyetModel();
    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());
    model.setId(entity.getId());

    model.setHopDongDamPhanTienTrinhId(entity.getHopDongDamPhanTienTrinhId());
    model.setGhiChu(entity.getGhiChu());
    model.setTrangThai(entity.getTrangThai());
    model.setNguoiDungId(entity.getNguoiDungId());

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

  @Column(name = "hop_dong_dam_phan_tien_trinh_id")
  protected Long hopDongDamPhanTienTrinhId;

  @Column(name = "ghi_chu")
  protected String ghiChu;

  @Column(name = "trang_thai")
  @Comment("1: TU_CHOI, 2: PHE_DUYET")
  @Enumerated(EnumType.ORDINAL)
  protected TrangThaiDamPhanEnum trangThai;

  @Column(name = "nguoi_dung_id")
  protected Long nguoiDungId;

  @Transient
  protected NguoiDungModel nguoiDung;
}
