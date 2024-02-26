package com.contract.danhmuc.phongdai.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.chucvu.chucvu.entity.ChucVuEntity;
import com.contract.danhmuc.phongdai.model.DmPhongDaiModel;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;
import com.contract.tram.tram.entity.TramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_phong_dai")
@Where(clause = "deleted_at IS NULL")
// @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class DmPhongDaiEntity extends DmPhongDaiModel {
    @OneToMany(mappedBy = "dmPhongDaiEntity", fetch = FetchType.LAZY)
    private Set<TramEntity> tramEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "dmPhongDaiEntity", fetch = FetchType.EAGER)
    private Set<ChucVuEntity> chucVuEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "dmPhongDaiEntity", fetch = FetchType.EAGER)
    private Set<NguoiDungKhuVucEntity> nguoiDungKhuVucEntitySet = new HashSet<>();
}
