package com.contract.tram.loailichsu.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.tram.loailichsu.model.LoaiLichSuModel;
import com.contract.tram.tramlichsu.entity.TramLichSuEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "loai_lichsu")
@Where(clause = "deleted_at IS NULL")
public class LoaiLichSuEntity extends LoaiLichSuModel {
    @OneToMany(mappedBy = "loaiLichSuEntity")
    private Set<TramLichSuEntity> tramLichSuEntityList = new HashSet<>();
}
