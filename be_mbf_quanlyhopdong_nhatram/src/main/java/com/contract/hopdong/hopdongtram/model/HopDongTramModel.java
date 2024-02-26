package com.contract.hopdong.hopdongtram.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;

import com.contract.common.entity.AuditEntity;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.hopdong.hopdongfile.model.HopDongFileModel;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.contract.hopdong.hopdongtram.enums.TrangThaiHopDongTramEnum;
import com.contract.hopdong.hopdongtram_dungchung.model.HopDongTramDungChungModel;
import com.contract.hopdong.hopdongtram_kythanhtoan.model.HopDongTramKyThanhToanModel;
import com.contract.hopdong.hopdongtram_phutro.model.HopDongTramPhuTroModel;
import com.contract.tram.tram.model.TramModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongTramModel extends AuditEntity<String> {
  public static HopDongTramModel fromEntity(HopDongTramEntity entity, boolean containChild) {
    HopDongTramModel model = new HopDongTramModel();
    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());
    model.setId(entity.getId());

    model.setTramId(entity.getTramId());
    model.setHopDongId(entity.getHopDongId());
    model.setGiaThue(entity.getGiaThue());
    model.setGiaDienKhoan(entity.getGiaDienKhoan());
    model.setTrangThaiHoatDong(entity.getTrangThaiHoatDong());
    model.setNgayBatDauYeuCauThanhToan(entity.getNgayBatDauYeuCauThanhToan());

    if (containChild) {
      try {
        if (entity.getHopDongTramDungChungEntity() != null
            && !(entity.getHopDongTramDungChungEntity() instanceof HibernateProxy)) {
          model.setHopDongTramDungChung(
              HopDongTramDungChungModel.fromEntity(entity.getHopDongTramDungChungEntity(), true));
        }
      } catch (Exception e) {
        model.setHopDongTramDungChung(null);
      }

      try {
        if (entity.getHopDongTramKyThanhToanEntities() != null) {
          model.setHopDongTramKyThanhToanList(entity.getHopDongTramKyThanhToanEntities().stream()
              .map(hdTramKyTTEntity -> HopDongTramKyThanhToanModel.fromEntity(hdTramKyTTEntity, true))
              .collect(Collectors.toList()));
        }
      } catch (Exception e) {
        model.setHopDongTramKyThanhToanList(null);
      }

      try {
        if (entity.getHopDongTramPhuTroEntities() != null) {
          model.setHopDongTramPhuTroList(entity.getHopDongTramPhuTroEntities().stream()
              .map(hdPtEntity -> HopDongTramPhuTroModel.fromEntity(hdPtEntity, true))
              .collect(Collectors.toList()));
        }
      } catch (Exception e) {
        model.setHopDongTramPhuTroList(null);
      }

      if (entity.getTramEntity() != null && !(entity.getHopDongTramDungChungEntity() instanceof HibernateProxy)) {
        model.setTram(TramModel.fromEntity(entity.getTramEntity(), true));
      }
    }
    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "tram_id", nullable = false)
  protected Long tramId;

  @Column(name = "hop_dong_id", nullable = false)
  protected Long hopDongId;

  @Column(name = "gia_thue")
  protected Double giaThue;

  @Column(name = "gia_dien_khoan")
  protected Double giaDienKhoan;

  @Column(name = "trang_thai_hoat_dong")
  @Comment("0: ngừng hoạt động; 1: hoạt ")
  @Enumerated(EnumType.ORDINAL)
  protected TrangThaiHopDongTramEnum trangThaiHoatDong;

  @Column(name = "ngay_bat_dau_yeu_cau_thanh_toan")
  protected Date ngayBatDauYeuCauThanhToan;

  @Transient
  protected TramModel tram;

  @Transient
  protected HopDongModel hopDong;

  @Transient
  protected HopDongTramDungChungModel hopDongTramDungChung;

  @Transient
  protected List<HopDongTramKyThanhToanModel> hopDongTramKyThanhToanList;

  @Transient
  protected List<HopDongTramPhuTroModel> hopDongTramPhuTroList;

  @Transient
  protected List<HopDongFileModel> hopDongFileList;
}
