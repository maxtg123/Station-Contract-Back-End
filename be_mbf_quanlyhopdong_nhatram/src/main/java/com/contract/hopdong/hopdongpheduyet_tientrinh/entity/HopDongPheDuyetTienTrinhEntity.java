package com.contract.hopdong.hopdongpheduyet_tientrinh.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
import com.contract.hopdong.hopdongpheduyet_tientrinh.model.HopDongPheDuyetTienTrinhModel;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_phe_duyet_tien_trinh")
@Where(clause = "deleted_at IS NULL")
public class HopDongPheDuyetTienTrinhEntity extends HopDongPheDuyetTienTrinhModel {
  public static HopDongPheDuyetTienTrinhEntity fromModel(HopDongPheDuyetTienTrinhEntity entity,
      HopDongPheDuyetTienTrinhModel model) {
    if (entity == null) {
      entity = new HopDongPheDuyetTienTrinhEntity();
    }

    entity.setHopDongPheDuyetId(model.getHopDongPheDuyetId());
    entity.setGhiChu(model.getGhiChu());
    entity.setTrangThaiPheDuyet(model.getTrangThaiPheDuyet());
    entity.setNguoiDungId(model.getNguoiDungId());
    entity.setChangeLog(model.getChangeLog());
    entity.setChangeLogClob(model.getChangeLogClob());

    return entity;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hop_dong_phe_duyet_id", insertable = false, updatable = false)
  private HopDongPheDuyetEntity hopDongPheDuyetEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nguoi_dung_id", insertable = false, updatable = false)
  private NguoiDungEntity nguoiDungEntity;
}
