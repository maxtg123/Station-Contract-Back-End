package com.contract.danhmuc.to.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.to.model.DmToModel;
import com.contract.nguoidung.nguoidungkhuvuc.entity.NguoiDungKhuVucEntity;
import com.contract.tram.tram.entity.TramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_to")
@Where(clause = "deleted_at IS NULL")
public class DmToEntity extends DmToModel {
    @OneToMany(mappedBy = "dmToEntity", fetch = FetchType.LAZY)
    private Set<TramEntity> tramEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "dmToEntity", fetch = FetchType.LAZY)
    private Set<NguoiDungKhuVucEntity> nguoiDungKhuVucEntitySet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phong_dai_id", insertable = false, updatable = false)
    private DmPhongDaiEntity dmPhongDaiEntity;
}
