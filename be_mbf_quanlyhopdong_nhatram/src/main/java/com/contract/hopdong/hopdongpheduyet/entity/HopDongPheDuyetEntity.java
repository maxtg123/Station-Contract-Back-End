package com.contract.hopdong.hopdongpheduyet.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetModel;
import com.contract.hopdong.hopdongpheduyet_nguoinhan.entity.HopDongPheDuyetNguoiNhanEntity;
import com.contract.hopdong.hopdongpheduyet_tientrinh.entity.HopDongPheDuyetTienTrinhEntity;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_phe_duyet")
@Where(clause = "deleted_at IS NULL")
public class HopDongPheDuyetEntity extends HopDongPheDuyetModel {
  public static HopDongPheDuyetEntity fromModel(HopDongPheDuyetEntity entity, HopDongPheDuyetModel model) {
    if (entity == null) {
      entity = new HopDongPheDuyetEntity();
    }

    entity.setHopDongId(model.getHopDongId());
    entity.setTrangThaiPheDuyetMoiNhat(model.getTrangThaiPheDuyetMoiNhat());
    entity.setGhiChu(model.getGhiChu());
    entity.setNguoiGuiId(model.getNguoiGuiId());
    entity.setLoaiXetDuyet(model.getLoaiXetDuyet());

    return entity;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nguoi_gui_id", insertable = false, updatable = false)
  private NguoiDungEntity nguoiGuiEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hop_dong_id", insertable = false, updatable = false)
  private HopDongEntity hopDongEntity;

  @OneToMany(mappedBy = "hopDongPheDuyetEntity", fetch = FetchType.LAZY)
  private Set<HopDongPheDuyetNguoiNhanEntity> hopDongPheDuyetNguoiNhanEntityList;

  @OneToMany(mappedBy = "hopDongPheDuyetEntity", fetch = FetchType.LAZY)
  @OrderBy("createdAt desc")
  @JsonIgnore
  private Set<HopDongPheDuyetTienTrinhEntity> hopDongPheDuyetTienTrinhEnities = new HashSet<>();
}
