package com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdongdamphan_tientrinh.entity.HopDongDamPhanTienTrinhEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.model.HopDongDamPhanTienTrinhXetDuyetModel;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_dam_phan_tien_trinh_xetduyet")
@Where(clause = "deleted_at IS NULL")
public class HopDongDamPhanTienTrinhXetDuyetEntity extends HopDongDamPhanTienTrinhXetDuyetModel {

  public static HopDongDamPhanTienTrinhXetDuyetEntity fromModel(HopDongDamPhanTienTrinhXetDuyetEntity entity,
      HopDongDamPhanTienTrinhXetDuyetModel model) {
    if (entity == null) {
      entity = new HopDongDamPhanTienTrinhXetDuyetEntity();
    }

    entity.setHopDongDamPhanTienTrinhId(model.getHopDongDamPhanTienTrinhId());
    entity.setGhiChu(model.getGhiChu());
    entity.setTrangThai(model.getTrangThai());
    entity.setNguoiDungId(model.getNguoiDungId());

    return entity;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nguoi_dung_id", insertable = false, updatable = false)
  private NguoiDungEntity nguoiDungEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hop_dong_dam_phan_tien_trinh_id", insertable = false, updatable = false)
  private HopDongDamPhanTienTrinhEntity hopDongDamPhanTienTrinhEntity;
}
