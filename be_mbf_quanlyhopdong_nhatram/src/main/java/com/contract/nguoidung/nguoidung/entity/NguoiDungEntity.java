package com.contract.nguoidung.nguoidung.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdongdamphan.entity.HopDongDamPhanEntity;
import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
import com.contract.hopdong.hopdongpheduyet_nguoinhan.entity.HopDongPheDuyetNguoiNhanEntity;
import com.contract.hopdong.hopdongphuluc.entity.HopDongPhuLucEntity;
import com.contract.hopdong.hopdongtram_kythanhtoan.entity.HopDongTramKyThanhToanEntity;
import com.contract.log.entity.LogEntity;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "nguoi_dung")
@Where(clause = "deleted_at IS NULL")
public class NguoiDungEntity extends NguoiDungModel {
    @OneToMany(mappedBy = "nguoiDungEntity", fetch = FetchType.EAGER)
    private Set<NguoiDungKhuVucEntity> nguoiDungKhuVucEntityList;

    @OneToMany(mappedBy = "nguoiDungEntity", fetch = FetchType.LAZY)
    private Set<LogEntity> logEntityList;

    @OneToMany(mappedBy = "nguoiDungEntity", fetch = FetchType.LAZY)
    private Set<HopDongTramKyThanhToanEntity> hopDongTramKyThanhToanEntityList = new HashSet<>();

    @OneToMany(mappedBy = "nguoiDungEntity", fetch = FetchType.LAZY)
    private Set<HopDongPhuLucEntity> hopDongPhuLucEntities = new HashSet<>();

    @OneToMany(mappedBy = "nguoiDungEntity", fetch = FetchType.LAZY)
    private Set<HopDongPheDuyetNguoiNhanEntity> hopDongPheDuyetNguoiNhanEntityList;

    @OneToMany(mappedBy = "nguoiGuiEntity", fetch = FetchType.LAZY)
    private Set<HopDongPheDuyetEntity> hopDongPheDuyetNguoiGuiEntityList;

    @OneToMany(mappedBy = "nguoiGuiEntity", fetch = FetchType.LAZY)
    private Set<HopDongDamPhanEntity> hopDongDamPhanEntities;;
}
