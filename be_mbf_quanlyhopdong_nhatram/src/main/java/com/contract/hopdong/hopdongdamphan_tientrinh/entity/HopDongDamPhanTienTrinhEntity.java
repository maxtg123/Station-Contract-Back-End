package com.contract.hopdong.hopdongdamphan_tientrinh.entity;

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

import com.contract.hopdong.hopdongdamphan.entity.HopDongDamPhanEntity;
import com.contract.hopdong.hopdongdamphan_file.entity.HopDongDamPhanFileEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh.model.HopDongDamPhanTienTrinhModel;
import com.contract.hopdong.hopdongdamphan_tientrinh_change.entity.HopDongDamPhanTienTrinhChangeEntity;
import com.contract.hopdong.hopdongdamphan_tientrinh_xetduyet.entity.HopDongDamPhanTienTrinhXetDuyetEntity;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong_dam_phan_tien_trinh")
@Where(clause = "deleted_at IS NULL")
public class HopDongDamPhanTienTrinhEntity extends HopDongDamPhanTienTrinhModel {

  public static HopDongDamPhanTienTrinhEntity fromModel(HopDongDamPhanTienTrinhEntity entity,
      HopDongDamPhanTienTrinhModel model) {
    if (entity == null) {
      entity = new HopDongDamPhanTienTrinhEntity();
    }

    entity.setHopDongDamPhanId(model.getHopDongDamPhanId());
    entity.setGhiChu(model.getGhiChu());
    entity.setTrangThai(model.getTrangThai());
    entity.setNguoiDungId(model.getNguoiDungId());

    return entity;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hop_dong_dam_phan_id", insertable = false, updatable = false)
  private HopDongDamPhanEntity hopDongDamPhanEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nguoi_dung_id", insertable = false, updatable = false)
  private NguoiDungEntity nguoiDungEntity;

  @OneToMany(mappedBy = "hopDongDamPhanTienTrinhEntity", fetch = FetchType.LAZY)
  @OrderBy("createdAt")
  private Set<HopDongDamPhanTienTrinhChangeEntity> hopDongDamPhanTienTrinhChangeEntities = new HashSet<>();

  @OneToMany(mappedBy = "hopDongDamPhanTienTrinhEntity", fetch = FetchType.LAZY)
  @OrderBy("createdAt")
  private Set<HopDongDamPhanFileEntity> hopDongDamPhanFileEntities = new HashSet<>();

  @OneToMany(mappedBy = "hopDongDamPhanTienTrinhEntity", fetch = FetchType.LAZY)
  @OrderBy("createdAt")
  private Set<HopDongDamPhanTienTrinhXetDuyetEntity> hopDongDamPhanTienTrinhXetDuyetEntities = new HashSet<>();
}
