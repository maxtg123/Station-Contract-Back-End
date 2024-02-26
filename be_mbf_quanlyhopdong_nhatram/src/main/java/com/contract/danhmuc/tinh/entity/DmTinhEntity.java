package com.contract.danhmuc.tinh.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.huyen.entity.DmHuyenEntity;
import com.contract.danhmuc.tinh.model.DmTinhModel;
import com.contract.tram.tram.entity.TramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_tinh")
@Where(clause = "deleted_at IS NULL")
public class DmTinhEntity extends DmTinhModel {
    @OneToMany(mappedBy = "dmTinhEntity", fetch = FetchType.LAZY)
    private Set<TramEntity> tramEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "dmTinhEntity", fetch = FetchType.LAZY)
    private Set<DmHuyenEntity> dmHuyenEntitySet = new HashSet<>();
}
