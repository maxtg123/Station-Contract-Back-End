package com.contract.nguoidung.nguoidungkhuvuc.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.chucvu.chucvu.entity.ChucVuEntity;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.to.entity.DmToEntity;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.nguoidung.nguoidungkhuvuc.model.NguoiDungKhuVucModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "nguoidung_khuvuc")
@Where(clause = "deleted_at IS NULL")
public class NguoiDungKhuVucEntity extends NguoiDungKhuVucModel {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nguoi_dung_id", insertable = false, updatable = false)
    private NguoiDungEntity nguoiDungEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phongdai_chucvu_id", insertable = false, updatable = false)
    private ChucVuEntity chucVuEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phong_dai_id", insertable = false, updatable = false)
    private DmPhongDaiEntity dmPhongDaiEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id", insertable = false, updatable = false)
    private DmToEntity dmToEntity;
}
