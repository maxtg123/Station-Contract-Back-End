package com.contract.hopdong.hopdong.model;

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
import com.contract.danhmuc.doituongkyhopdong.model.DmDoiTuongKyHopDongModel;
import com.contract.danhmuc.hinhthucdautu.model.DmHinhThucDauTuModel;
import com.contract.danhmuc.hinhthuckyhopdong.model.DmHinhThucKyHopDongModel;
import com.contract.danhmuc.hinhthucthanhtoan.model.DmHinhThucThanhToanModel;
import com.contract.danhmuc.khoanmuc.model.DmKhoanMucModel;
import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdong.enums.LoaiHopDongEnum;
import com.contract.hopdong.hopdong.enums.TinhTrangHopDongEnum;
import com.contract.hopdong.hopdong.enums.TrangThaiHopDongEnum;
import com.contract.hopdong.hopdongdamphan.model.HopDongDamPhanModel;
import com.contract.hopdong.hopdongdoitac.model.HopDongDoiTacModel;
import com.contract.hopdong.hopdongfile.model.HopDongFileModel;
import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetModel;
import com.contract.hopdong.hopdongphuluc.model.HopDongPhuLucModel;
import com.contract.hopdong.hopdongtram.model.HopDongTramModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class HopDongModel extends AuditEntity<String> {
  public static HopDongModel fromEntity(HopDongEntity entity, boolean containChild) {
    HopDongModel model = new HopDongModel();
    model.setId(entity.getId());
    model.setCreatedAt(entity.getCreatedAt());
    model.setUpdatedAt(entity.getUpdatedAt());

    model.setSoHopDong(entity.getSoHopDong());
    model.setSoHopDongErp(entity.getSoHopDongErp());
    model.setHinhThucKyId(entity.getHinhThucKyId());
    model.setHinhThucDauTuId(entity.getHinhThucDauTuId());
    model.setDoiTuongKyId(entity.getDoiTuongKyId());
    model.setHinhThucThanhToanId(entity.getHinhThucThanhToanId());
    model.setKhoanMucId(entity.getKhoanMucId());
    model.setCoKyQuy(entity.getCoKyQuy());
    model.setNgayKy(entity.getNgayKy());
    model.setNgayKetThuc(entity.getNgayKetThuc());
    model.setGhiChu(entity.getGhiChu());
    model.setThueVat(entity.getThueVat());
    model.setTrangThaiHopDong(entity.getTrangThaiHopDong());
    model.setTinhTrangHopDong(entity.getTinhTrangHopDong());
    model.setLoaiHopDong(entity.getLoaiHopDong());
    model.setChuKyNam(entity.getChuKyNam());
    model.setChuKyThang(entity.getChuKyThang());
    model.setChuKyNgay(entity.getChuKyNgay());
    model.setLoaiHopDong(entity.getLoaiHopDong());
    model.setGiaKyQuy(entity.getGiaKyQuy());
    if (containChild) {
      if (entity.getDmHinhThucKyHopDongEntity() != null
          && !(entity.getDmHinhThucKyHopDongEntity() instanceof HibernateProxy)) {
        model.setDmHinhThucKyHopDong(
            DmHinhThucKyHopDongModel.fromEntity(entity.getDmHinhThucKyHopDongEntity()));
      }
      if (entity.getDmHinhThucDauTuEntity() != null && !(entity.getDmHinhThucDauTuEntity() instanceof HibernateProxy)) {
        model
            .setDmHinhThucDauTu(DmHinhThucDauTuModel.fromEntity(entity.getDmHinhThucDauTuEntity()));
      }
      if (entity.getDmDoiTuongKyHopDongEntity() != null
          && !(entity.getDmDoiTuongKyHopDongEntity() instanceof HibernateProxy)) {
        model.setDmDoiTuongKyHopDong(
            DmDoiTuongKyHopDongModel.fromEntity(entity.getDmDoiTuongKyHopDongEntity()));
      }
      if (entity.getDmHinhThucThanhToanEntity() != null
          && !(entity.getDmHinhThucThanhToanEntity() instanceof HibernateProxy)) {
        model.setDmHinhThucThanhToan(
            DmHinhThucThanhToanModel.fromEntity(entity.getDmHinhThucThanhToanEntity()));
      }
      if (entity.getDmKhoanMucEntity() != null
          && !(entity.getDmKhoanMucEntity() instanceof HibernateProxy)) {
        model.setDmKhoanMuc(entity.getDmKhoanMucEntity());
      }
      if (entity.getHopDongDoiTacEntity() != null && !(entity.getHopDongDoiTacEntity() instanceof HibernateProxy)) {
        model.setHopDongDoiTac(
            HopDongDoiTacModel.fromEntity(entity.getHopDongDoiTacEntity(), false));
      }
      try {
        if (entity.getHopDongTramEntities() != null) {
          model.setHopDongTramList(entity.getHopDongTramEntities().stream()
              .map(hopDongTramEntity -> HopDongTramModel.fromEntity(hopDongTramEntity, true))
              .collect(Collectors.toList()));
        }
      } catch (Exception e) {
        // Avoid lazy loading error
        model.setHopDongTramList(null);
      }
      try {
        if (entity.getHopDongFileEntities() != null) {
          model.setHopDongFileModels(entity.getHopDongFileEntities().stream()
              .map(fileEntity -> HopDongFileModel.fromEntity(fileEntity, true)).collect(Collectors.toList()));
        }
      } catch (Exception e) {
        // Avoid lazy loading error
        model.setHopDongFileModels(null);
      }

      try {
        if (entity.getHopDongPheDuyetEntities() != null
            && !(entity.getHopDongPheDuyetEntities() instanceof HibernateProxy)) {
          model.setHopDongPheDuyetList(entity.getHopDongPheDuyetEntities().stream()
              .map(_entity -> HopDongPheDuyetModel.fromEntity(_entity, true)).collect(Collectors.toList()));
        }
      } catch (Exception e) {
        // Avoid lazy loading error
        model.setHopDongPheDuyetList(null);
      }

      try {
        if (entity.getHopDongDamPhanEntities() != null
            && !(entity.getHopDongDamPhanEntities() instanceof HibernateProxy)) {
          model.setHopDongDamPhanList(entity.getHopDongDamPhanEntities().stream()
              .map(_entity -> HopDongDamPhanModel.fromEntity(_entity, true)).collect(Collectors.toList()));
        }
      } catch (Exception e) {
        // Avoid lazy loading error
        model.setHopDongDamPhanList(null);
      }

      try {
        if (entity.getHopDongPhuLucEntities() != null
            && !(entity.getHopDongPhuLucEntities() instanceof HibernateProxy)) {
          model.setHopDongPhuLucModels(entity.getHopDongPhuLucEntities().stream()
              .map(_entity -> HopDongPhuLucModel.fromEntity(_entity, true)).collect(Collectors.toList()));
        }
      } catch (Exception e) {
        // Avoid lazy loading error
        model.setHopDongPhuLucModels(null);
      }
    }

    return model;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "so_hop_dong")
  protected String soHopDong;

  @Column(name = "so_hop_dong_erp")
  protected String soHopDongErp;

  @Column(name = "hinh_thuc_ky_id")
  protected Integer hinhThucKyId;

  @Column(name = "hinh_thuc_dau_tu_id")
  protected Integer hinhThucDauTuId;

  @Column(name = "doi_tuong_ky_id")
  protected Integer doiTuongKyId;

  @Column(name = "hinh_thuc_thanh_toan_id")
  protected Integer hinhThucThanhToanId;

  @Column(name = "co_ky_quy")
  protected Boolean coKyQuy;

  @Column(name = "ngay_ky")
  protected Date ngayKy;

  @Column(name = "ngay_ket_thuc")
  protected Date ngayKetThuc;

  @Column(name = "ghi_chu")
  protected String ghiChu;

  @Column(name = "thue_vat")
  protected Integer thueVat;

  @Column(name = "trang_thai_hop_dong")
  @Comment("0: nháp; 1: chờ phê duyêt hợp đồng; 2: chờ phê duyệt phụ lục; 3: hoạt động; 4: ngung hoat dong")
  @Enumerated(EnumType.ORDINAL)
  protected TrangThaiHopDongEnum trangThaiHopDong = TrangThaiHopDongEnum.NHAP;

  @Column(name = "loai_hop_dong")
  @Comment("0: mặt bằng; 1: xã hội hoá; 2: IBC")
  @Enumerated(EnumType.ORDINAL)
  protected LoaiHopDongEnum loaiHopDong;

  @Column(name = "chu_ky_nam")
  protected Integer chuKyNam;

  @Column(name = "chu_ky_thang")
  protected Integer chuKyThang;

  @Column(name = "chu_ky_ngay")
  protected Integer chuKyNgay;

  @Column(name = "gia_ky_quy")
  protected Double giaKyQuy;

  @Column(name = "khoan_muc_id")
  protected Integer khoanMucId;

  @Column(name = "tinh_trang_hop_dong")
  @Comment("0: KY_MOI, 1: TAI_KY, 2: DI_DOI, 3: THANH_LY, 4: PHU_LUC")
  @Enumerated(EnumType.ORDINAL)
  protected TinhTrangHopDongEnum tinhTrangHopDong;

  @Transient
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  protected DmHinhThucKyHopDongModel dmHinhThucKyHopDong;

  @Transient
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  protected DmHinhThucDauTuModel dmHinhThucDauTu;

  @Transient
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  protected DmDoiTuongKyHopDongModel dmDoiTuongKyHopDong;

  @Transient
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  protected DmHinhThucThanhToanModel dmHinhThucThanhToan;

  @Transient
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  protected DmKhoanMucModel dmKhoanMuc;

  @Transient
  protected HopDongDoiTacModel hopDongDoiTac;

  @Transient
  protected List<HopDongTramModel> hopDongTramList;

  @Transient
  protected List<HopDongPhuLucModel> hopDongPhuLucModels;

  @Transient
  protected List<HopDongFileModel> hopDongFileModels;

  @Transient
  protected List<HopDongPheDuyetModel> hopDongPheDuyetList;

  @Transient
  protected List<HopDongDamPhanModel> hopDongDamPhanList;
}
