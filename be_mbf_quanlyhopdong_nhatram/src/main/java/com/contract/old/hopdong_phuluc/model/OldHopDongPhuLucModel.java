package com.contract.old.hopdong_phuluc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class OldHopDongPhuLucModel {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  @Column(name = "so_hop_dong")
  protected String soHopDong;

  @Column(name = "ngay_ky")
  protected Date ngayKy;

  @Column(name = "ngay_hieu_luc")
  protected Date ngayHieuLuc;

  @Column(name = "ngay_ket_thuc")
  protected Date ngayKetThuc;

  @Column(name = "so_phu_luc")
  protected String soPhuLuc;

  @Column(name = "isactive")
  @Comment("2: active")
  protected Integer isactive;

  @Column(name = "ghiChu")
  protected String ghiChu;
}
