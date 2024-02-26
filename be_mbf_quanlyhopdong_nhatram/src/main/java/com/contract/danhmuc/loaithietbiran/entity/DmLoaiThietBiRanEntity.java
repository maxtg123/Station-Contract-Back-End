package com.contract.danhmuc.loaithietbiran.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.javers.core.metamodel.annotation.DiffIgnore;

import com.contract.danhmuc.loaithietbiran.model.DmLoaiThietBiRanModel;
import com.contract.tram.tram.entity.TramEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_thiet_bi_ran")
@Where(clause = "deleted_at IS NULL")
public class DmLoaiThietBiRanEntity extends DmLoaiThietBiRanModel {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tram_thietbi_ran", joinColumns = @JoinColumn(name = "loai_ran_id"), inverseJoinColumns = @JoinColumn(name = "tram_id"))
    @JsonIgnore
    @DiffIgnore
    private Set<TramEntity> tramEntitySet = new HashSet<>();
}
