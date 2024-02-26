package com.contract.danhmuc.loaicotangten.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.loaicotangten.model.DmLoaiCotAngtenModel;
import com.contract.tram.tram.entity.TramEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_cot_angten")
@Where(clause = "deleted_at IS NULL")
public class DmLoaiCotAngtenEntity extends DmLoaiCotAngtenModel {
    @OneToMany(mappedBy = "dmLoaiCotAngtenEntity")
    private Set<TramEntity> tramEntitySet = new HashSet<>();
}
