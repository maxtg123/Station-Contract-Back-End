package com.contract.tram.tramlichsu.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;
import com.contract.tram.loailichsu.entity.LoaiLichSuEntity;
import com.contract.tram.tram.entity.TramEntity;
import com.contract.tram.tramlichsu.model.TramLichSuModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "tram_lichsu")
@Where(clause = "deleted_at IS NULL")
public class TramLichSuEntity extends TramLichSuModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tram_id", insertable = false, updatable = false)
    private TramEntity tramEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_dung_id", insertable = false, updatable = false)
    private NguoiDungEntity nguoiDungEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_id", insertable = false, updatable = false)
    private LoaiLichSuEntity loaiLichSuEntity;
}
