package com.contract.chucvu.chucvu.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.chucvu.chucvu.model.ChucVuModel;
import com.contract.chucvu.phanquyen.entity.ChucVuPhanQuyenEntity;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "pd_chucvu")
@Where(clause = "deleted_at IS NULL")
public class ChucVuEntity extends ChucVuModel {
    // @JsonIgnore
    // @OneToMany(mappedBy = "chucVuEntity", )
    // private List<NguoiDungEntity> listNguoiDungEntity = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "chucVuEntity", fetch = FetchType.EAGER)
    private Set<NguoiDungKhuVucEntity> nguoiDungKhuVucEntityList = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "chucVuEntity", fetch = FetchType.EAGER)
    private Set<ChucVuPhanQuyenEntity> listChucVuPhanQuyenEntity = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phong_dai_id", insertable = false, updatable = false)
    private DmPhongDaiEntity dmPhongDaiEntity;
}