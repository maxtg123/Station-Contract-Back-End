package com.contract.danhmuc.loaicsht.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.loaicsht.model.DmLoaiCshtModel;
import com.contract.tram.tram.entity.TramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_csht")
@Where(clause = "deleted_at IS NULL")
public class DmLoaiCshtEntity extends DmLoaiCshtModel {
    @OneToMany(mappedBy = "dmLoaiCshtEntity")
    private Set<TramEntity> tramEntitySet = new HashSet<>();
}
