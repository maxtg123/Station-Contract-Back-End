package com.contract.hopdong.hopdongtram_kythanhtoan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.proxy.HibernateProxy;

import com.contract.hopdong.hopdongtram.model.HopDongTramModel;
import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongTramKyThanhToanModel {
  public static HopDongTramKyThanhToanModel fromEntity(HopDongTramKyThanhToanEntity entity,
      boolean containChild) {
    HopDongTramKyThanhToanModel model = new HopDongTramKyThanhToanModel();
    model.setId(entity.getId());

    model.setHopDongTramId(entity.getHopDongTramId());
    model.setTuNgay(entity.getTuNgay());
    model.setDenNgay(entity.getDenNgay());
    model.setGiaTien(entity.getGiaTien());
    model.setDaThanhToanNgay(entity.getDaThanhToanNgay());
    model.setThanhToanBy(entity.getThanhToanBy());

    if (containChild) {
      if (entity.getNguoiDungEntity() != null && !(entity.getNguoiDungEntity() instanceof HibernateProxy)) {
        model.setNguoiThanhToan(NguoiDungModel.fromEntity(entity.getNguoiDungEntity(), false));
      }
    }

    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "hop_dong_tram_id")
  protected Long hopDongTramId;

  @Column(name = "tu_ngay")
  protected Date tuNgay;

  @Column(name = "den_ngay")
  protected Date denNgay;

  @Column(name = "gia_tien")
  protected Double giaTien;

  @Column(name = "da_thanh_toan_ngay")
  protected Date daThanhToanNgay;

  @Column(name = "thanh_toan_by")
  protected Long thanhToanBy;

  @Transient
  protected HopDongTramModel hopDongTramModel;

  @Transient
  protected NguoiDungModel nguoiThanhToan;
}
