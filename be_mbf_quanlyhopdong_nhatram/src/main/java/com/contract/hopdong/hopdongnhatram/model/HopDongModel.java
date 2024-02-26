// package com.contract.hopdong.hopdongnhatram.model;

// import java.util.Date;
// import java.util.List;

// import javax.persistence.Column;
// import javax.persistence.EnumType;
// import javax.persistence.Enumerated;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.MappedSuperclass;
// import javax.persistence.Transient;

// import com.contract.common.entity.AuditEntity;
// import com.contract.danhmuc.doituongkyhopdong.model.DmDoiTuongKyHopDongModel;
// import com.contract.danhmuc.hinhthucdautu.model.DmHinhThucDauTuModel;
// import com.contract.danhmuc.hinhthuckyhopdong.model.DmHinhThucKyHopDongModel;
// import com.contract.danhmuc.hinhthucthanhtoan.model.DmHinhThucThanhToanModel;
// import com.contract.danhmuc.loaiphongmay.model.DmLoaiPhongMayModel;
// import
// com.contract.danhmuc.loaiphongmayphatdien.model.DmLoaiPhongMayPhatDienModel;
// import com.contract.danhmuc.loaitramvhkt.model.DmLoaiTramVHKTModel;
// import com.contract.hopdong.hopdongdoitac.model.HopDongDoiTacModel;
// import com.contract.hopdong.hopdongfile.model.HopDongFileModel;
// import com.contract.hopdong.hopdongkythanhtoan.model.HopDongKyThanhToanModel;
// import com.contract.hopdong.hopdongnhatram.entity.HopDongEntity;
// import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetModel;
// import com.contract.hopdong.hopdongphutro.model.HopDongPhuTroModel;
// import
// com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;
// import com.contract.tram.tram.model.TramModel;
// import com.fasterxml.jackson.annotation.JsonProperty;

// import lombok.AccessLevel;
// import lombok.Getter;
// import lombok.Setter;

// @Getter(AccessLevel.PUBLIC)
// @Setter(AccessLevel.PUBLIC)
// @MappedSuperclass
// public class HopDongModel extends AuditEntity<String> {
// public static HopDongModel fromEntity(HopDongEntity entity, boolean
// containChild) {
// HopDongModel hopDongModel = new HopDongModel();

// try {
// hopDongModel.setCreatedAt(entity.getCreatedAt());
// hopDongModel.setUpdatedAt(entity.getUpdatedAt());

// hopDongModel.setId(entity.getId());
// hopDongModel.setTramId(entity.getTramId());
// hopDongModel.setSoHopDong(entity.getSoHopDong());
// hopDongModel.setSoHopDongErp(entity.getSoHopDongErp());
// hopDongModel.setHinhThucKyId(entity.getHinhThucKyId());
// hopDongModel.setHinhThucDauTuId(entity.getHinhThucDauTuId());
// hopDongModel.setDoiTuongKyId(entity.getDoiTuongKyId());
// hopDongModel.setCoKyQuy(entity.getCoKyQuy());
// hopDongModel.setNgayKy(entity.getNgayKy());
// hopDongModel.setNgayKetThuc(entity.getNgayKetThuc());
// hopDongModel.setGhiChu(entity.getGhiChu());
// hopDongModel.setGiaThue(entity.getGiaThue());
// hopDongModel.setThueVat(entity.getThueVat());
// hopDongModel.setHinhThucThanhToanId(entity.getHinhThucThanhToanId());
// hopDongModel.setChuKyNam(entity.getChuKyNam());
// hopDongModel.setChuKyThang(entity.getChuKyThang());
// hopDongModel.setChuKyNgay(entity.getChuKyNgay());
// hopDongModel.setTrangThai(entity.getTrangThai());
// hopDongModel.setNgayBatDauYeuCauThanhToan(entity.getNgayBatDauYeuCauThanhToan());
// hopDongModel.setGiaDienKhoan(entity.getGiaDienKhoan());
// hopDongModel.setTinhTrangHopDong(entity.getTinhTrangHopDong());
// hopDongModel.setLoaiPhongMayPhatDienId(entity.getLoaiPhongMayPhatDienId());
// hopDongModel.setLoaiTramVHKTId(entity.getLoaiTramVHKTId());
// hopDongModel.setLoaiPhongMayId(entity.getLoaiPhongMayId());

// if (containChild) {
// if (entity.getTramEntity() != null) {
// hopDongModel.setTram(TramModel.fromEntity(entity.getTramEntity(), false));
// }
// if (entity.getDmHinhThucKyHopDongEntity() != null) {
// hopDongModel.setDmHinhThucKyHopDong(
// DmHinhThucKyHopDongModel.fromEntity(entity.getDmHinhThucKyHopDongEntity()));
// }
// if (entity.getDmHinhThucDauTuEntity() != null) {
// hopDongModel.setDmHinhThucDauTu(DmHinhThucDauTuModel.fromEntity(entity.getDmHinhThucDauTuEntity()));
// }
// if (entity.getDmDoiTuongKyHopDongEntity() != null) {
// hopDongModel.setDmDoiTuongKyHopDong(
// DmDoiTuongKyHopDongModel.fromEntity(entity.getDmDoiTuongKyHopDongEntity()));
// }
// if (entity.getDmHinhThucThanhToanEntity() != null) {
// hopDongModel.setDmHinhThucThanhToan(
// DmHinhThucThanhToanModel.fromEntity(entity.getDmHinhThucThanhToanEntity()));
// }
// // if (entity.getHopDongDoiTacEntity() != null) {
// //
// hopDongModel.setHopDongDoiTac(HopDongDoiTacModel.fromEntity(entity.getHopDongDoiTacEntity(),
// // false));
// // }
// // if (entity.getHopDongDungChungEntity() != null) {
// // hopDongModel.setHopDongDungChung(
// // HopDongDungChungModel.fromEntity(entity.getHopDongDungChungEntity(),
// false));
// // }
// if (entity.getDmLoaiPhongMayPhatDienEntity() != null) {
// hopDongModel.setDmLoaiPhongMayPhatDien(
// DmLoaiPhongMayPhatDienModel.fromEntity(entity.getDmLoaiPhongMayPhatDienEntity()));
// }
// if (entity.getDmLoaiTramVHKTEntity() != null) {
// hopDongModel.setDmLoaiTramVHKT(DmLoaiTramVHKTModel.fromEntity(entity.getDmLoaiTramVHKTEntity()));
// }
// if (entity.getDmLoaiPhongMayEntity() != null) {
// hopDongModel.setDmLoaiPhongMay(DmLoaiPhongMayModel.fromEntity(entity.getDmLoaiPhongMayEntity()));
// }
// }
// } catch (Exception e) {
// System.out.println(e);
// }

// return hopDongModel;
// }

// @Id
// @GeneratedValue(strategy = GenerationType.AUTO)
// protected Long id = 0L;

// @Column(name = "tram_id", nullable = false)
// protected Long tramId;

// @Column(name = "so_hop_dong")
// protected String soHopDong;

// @Column(name = "so_hop_dong_erp")
// protected String soHopDongErp;

// @Column(name = "hinh_thuc_ky_id")
// protected Integer hinhThucKyId;

// @Column(name = "hinh_thuc_dau_tu_id")
// protected Integer hinhThucDauTuId;

// @Column(name = "doi_tuong_ky_id")
// protected Integer doiTuongKyId;

// @Column(name = "co_ky_quy")
// protected Boolean coKyQuy;

// @Column(name = "ngay_ky")
// protected Date ngayKy;

// @Column(name = "ngay_ket_thuc")
// protected Date ngayKetThuc;

// @Column(name = "ghi_chu")
// protected String ghiChu;

// @Column(name = "gia_thue")
// protected Double giaThue;

// @Column(name = "thue_vat")
// protected Integer thueVat;

// @Column(name = "hinh_thuc_thanh_toan_id")
// protected Integer hinhThucThanhToanId;

// @Column(name = "chu_ky_nam")
// protected Integer chuKyNam;

// @Column(name = "chu_ky_thang")
// protected Integer chuKyThang;

// @Column(name = "chu_ky_ngay")
// protected Integer chuKyNgay;

// @Column(name = "trang_thai")
// @Enumerated(EnumType.ORDINAL)
// protected TrangThaiHopDong trangThai = TrangThaiHopDong.NHAP;

// @Column(name = "tinh_trang_hop_dong")
// @Enumerated(EnumType.ORDINAL)
// protected TinhTrangHopDong tinhTrangHopDong = TinhTrangHopDong.MOI;

// @Column(name = "ngay_bat_dau_yeu_cau_thanh_toan")
// protected Date ngayBatDauYeuCauThanhToan;

// @Column(name = "gia_dien_khoan")
// protected Double giaDienKhoan;

// @Column(name = "dm_loai_phong_may_phat_dien_id")
// protected Integer loaiPhongMayPhatDienId;

// @Column(name = "dm_loai_tram_vhkt_id")
// protected Integer loaiTramVHKTId;

// @Column(name = "dm_loai_phong_may_id")
// protected Integer loaiPhongMayId;

// @Transient
// // @JsonProperty(access = JsonProperty.Access.READ_ONLY)
// protected TramModel tram;

// @Transient
// @JsonProperty(access = JsonProperty.Access.READ_ONLY)
// protected DmHinhThucKyHopDongModel dmHinhThucKyHopDong;

// @Transient
// @JsonProperty(access = JsonProperty.Access.READ_ONLY)
// protected DmHinhThucDauTuModel dmHinhThucDauTu;

// @Transient
// @JsonProperty(access = JsonProperty.Access.READ_ONLY)
// protected DmDoiTuongKyHopDongModel dmDoiTuongKyHopDong;

// @Transient
// @JsonProperty(access = JsonProperty.Access.READ_ONLY)
// protected DmHinhThucThanhToanModel dmHinhThucThanhToan;

// @Transient
// protected HopDongDoiTacModel hopDongDoiTac;

// @Transient
// protected HopDongTramDungChungEntity hopDongTramDungChungEntity;

// @Transient
// protected DmLoaiPhongMayPhatDienModel dmLoaiPhongMayPhatDien;

// @Transient
// protected DmLoaiPhongMayModel dmLoaiPhongMay;

// @Transient
// protected DmLoaiTramVHKTModel dmLoaiTramVHKT;

// @Transient
// protected List<HopDongFileModel> fileList;

// @Transient
// protected List<HopDongPhuTroModel> hopDongPhuTroList;

// @Transient
// protected List<HopDongKyThanhToanModel> hopDongKyThanhToanList;

// // @Transient
// // protected List<HopDongPhuLucModel> hopDongPhuLucList;

// @Transient
// protected List<HopDongPheDuyetModel> hopDongPheDuyetList;

// @Override
// public Object clone() throws CloneNotSupportedException {
// return super.clone();
// }
// }
