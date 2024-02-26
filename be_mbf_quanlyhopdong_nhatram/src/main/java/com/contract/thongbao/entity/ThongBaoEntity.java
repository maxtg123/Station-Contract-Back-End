package com.contract.thongbao.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.thongbao.model.ThongBaoModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "thong_bao")
@Where(clause = "deleted_at IS NULL")
public class ThongBaoEntity extends ThongBaoModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_gui_id", insertable = false, updatable = false)
    private NguoiDungEntity nguoiGuiEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_nhan_id", insertable = false, updatable = false)
    private NguoiDungEntity nguoiNhanEntity;
}
