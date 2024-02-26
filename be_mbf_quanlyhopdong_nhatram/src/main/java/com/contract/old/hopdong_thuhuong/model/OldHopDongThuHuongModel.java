package com.contract.old.hopdong_thuhuong.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "old_hop_dong_thu_huong")
public class OldHopDongThuHuongModel {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "so_hop_dong")
  protected String soHopDong;

  @Column(name = "chu_tai_khoan", nullable = true)
  protected String chuTaiKhoan;

  @Column(name = "so_tai_khoan", nullable = true)
  protected String soTaiKhoan;

  @Column(name = "ngan_hang", nullable = true)
  protected String nganHang;

  @Column(name = "chi_nhanh", nullable = true)
  protected String chiNhanh;

  @Column(name = "trang_thai_hd_pl", nullable = true)
  @Comment("1: Di chuyen, 2: Tai ky, 3: Ky moi, 5: Phu luc")
  protected Integer trangThaiHopDongPhuLuc;

  @Column(name = "ma_site")
  protected String maSite;

  @Column(name = "don_vi_quan_ly")
  @Comment("1: Tay, 2 Dong, 8 BD, 9 DN, 10 CT, 11 TG")
  protected Integer donViQuanLy;

  @Column(name = "ten_phong_ban")
  protected String tenPhongBan;
}
