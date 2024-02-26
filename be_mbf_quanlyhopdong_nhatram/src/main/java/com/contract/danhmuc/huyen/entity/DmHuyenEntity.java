package com.contract.danhmuc.huyen.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.huyen.model.DmHuyenModel;
import com.contract.danhmuc.tinh.entity.DmTinhEntity;
import com.contract.danhmuc.xa.entity.DmXaEntity;
import com.contract.tram.tram.entity.TramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_huyen")
@Where(clause = "deleted_at IS NULL")
public class DmHuyenEntity extends DmHuyenModel {
    @OneToMany(mappedBy = "dmHuyenEntity", fetch = FetchType.LAZY)
    private Set<DmXaEntity> dmXaEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "dmHuyenEntity", fetch = FetchType.LAZY)
    private Set<TramEntity> tramEntitySet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tinh_id", insertable = false, updatable = false)
    private DmTinhEntity dmTinhEntity;
}
