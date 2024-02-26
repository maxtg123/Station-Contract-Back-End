package com.contract.danhmuc.xa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.huyen.entity.DmHuyenEntity;
import com.contract.danhmuc.xa.model.DmXaModel;
import com.contract.tram.tram.entity.TramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_xa")
@Where(clause = "deleted_at IS NULL")
public class DmXaEntity extends DmXaModel {
    @OneToMany(mappedBy = "dmXaEntity", fetch = FetchType.LAZY)
    private Set<TramEntity> tramEntitySet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "huyen_id", insertable = false, updatable = false)
    private DmHuyenEntity dmHuyenEntity;
}
