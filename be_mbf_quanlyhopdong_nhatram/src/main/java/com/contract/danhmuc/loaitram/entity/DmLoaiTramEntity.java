package com.contract.danhmuc.loaitram.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.loaitram.model.DmLoaiTramModel;
import com.contract.tram.tram.entity.TramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_tram")
@Where(clause = "deleted_at IS NULL")
public class DmLoaiTramEntity extends DmLoaiTramModel {
    @OneToMany(mappedBy = "dmLoaiTramEntity")
    private Set<TramEntity> tramEntitySet = new HashSet<>();
}
