package com.contract.danhmuc.tramkhuvuc.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.tramkhuvuc.model.DmTramKhuVucModel;
import com.contract.tram.tram.entity.TramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_tram_khuvuc")
@Where(clause = "deleted_at IS NULL")
public class DmTramKhuVucEntity extends DmTramKhuVucModel {
    @OneToMany(mappedBy = "dmTramKhuVucEntity")
    private Set<TramEntity> tramEntitySet = new HashSet<>();
}
