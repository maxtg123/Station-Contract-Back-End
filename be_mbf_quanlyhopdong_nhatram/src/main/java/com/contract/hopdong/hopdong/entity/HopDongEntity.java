package com.contract.hopdong.hopdong.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.doituongkyhopdong.entity.DmDoiTuongKyHopDongEntity;
import com.contract.danhmuc.hinhthucdautu.entity.DmHinhThucDauTuEntity;
import com.contract.danhmuc.hinhthuckyhopdong.entity.DmHinhThucKyHopDongEntity;
import com.contract.danhmuc.hinhthucthanhtoan.entity.DmHinhThucThanhToanEntity;
import com.contract.danhmuc.khoanmuc.model.DmKhoanMucModel;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.hopdong.hopdongdamphan.entity.HopDongDamPhanEntity;
import com.contract.hopdong.hopdongdoitac.entity.HopDongDoiTacEntity;
import com.contract.hopdong.hopdongfile.entity.HopDongFileEntity;
import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
import com.contract.hopdong.hopdongphuluc.entity.HopDongPhuLucEntity;
import com.contract.hopdong.hopdongtram.entity.HopDongTramEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "hop_dong")
@Where(clause = "deleted_at IS NULL")
public class HopDongEntity extends HopDongModel {
  public static HopDongEntity fromModel(HopDongEntity entity, HopDongModel model) {
    if (entity == null) {
      entity = new HopDongEntity();
    }
    // HopDongEntity entity = new HopDongEntity();
    entity.setSoHopDong(model.getSoHopDong());
    entity.setSoHopDongErp(model.getSoHopDongErp());
    entity.setHinhThucKyId(model.getHinhThucKyId());
    entity.setHinhThucDauTuId(model.getHinhThucDauTuId());
    entity.setDoiTuongKyId(model.getDoiTuongKyId());
    entity.setHinhThucThanhToanId(model.getHinhThucThanhToanId());
    entity.setCoKyQuy(model.getCoKyQuy());
    if (model.getCoKyQuy() == false) {
      entity.setGiaKyQuy(null);
    } else {
      entity.setGiaKyQuy(model.getGiaKyQuy());
    }
    entity.setNgayKy(model.getNgayKy());
    entity.setNgayKetThuc(model.getNgayKetThuc());
    entity.setGhiChu(model.getGhiChu());
    entity.setThueVat(model.getThueVat());
    entity.setTrangThaiHopDong(model.getTrangThaiHopDong());
    entity.setTinhTrangHopDong(model.getTinhTrangHopDong());
    entity.setLoaiHopDong(model.getLoaiHopDong());
    entity.setChuKyNam(model.getChuKyNam());
    entity.setChuKyThang(model.getChuKyThang());
    entity.setChuKyNgay(model.getChuKyNgay());

    entity.setKhoanMucId(model.getKhoanMucId());
    return entity;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hinh_thuc_ky_id", insertable = false, updatable = false)
  private DmHinhThucKyHopDongEntity dmHinhThucKyHopDongEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hinh_thuc_dau_tu_id", insertable = false, updatable = false)
  private DmHinhThucDauTuEntity dmHinhThucDauTuEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "doi_tuong_ky_id", insertable = false, updatable = false)
  private DmDoiTuongKyHopDongEntity dmDoiTuongKyHopDongEntity;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hinh_thuc_thanh_toan_id", insertable = false, updatable = false)
  private DmHinhThucThanhToanEntity dmHinhThucThanhToanEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "khoan_muc_id", insertable = false, updatable = false)
  private DmKhoanMucModel dmKhoanMucEntity;

  @OneToOne(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
  private HopDongDoiTacEntity hopDongDoiTacEntity;

  @JsonIgnore
  @OneToMany(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
  @NotNull
  @OrderBy("createdAt asc")
  private Set<HopDongTramEntity> hopDongTramEntities = new HashSet<>();

  @OneToMany(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
  @OrderBy("createdAt asc")
  private Set<HopDongPhuLucEntity> hopDongPhuLucEntities = new HashSet<>();

  @OneToMany(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
  @OrderBy("createdAt asc")
  private Set<HopDongFileEntity> hopDongFileEntities = new HashSet<>();

  @OneToMany(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
  @JsonIgnore
  private Set<HopDongPheDuyetEntity> hopDongPheDuyetEntities = new HashSet<>();

  @OneToMany(mappedBy = "hopDongEntity", fetch = FetchType.LAZY)
  @JsonIgnore
  private Set<HopDongDamPhanEntity> hopDongDamPhanEntities = new HashSet<>();
}
