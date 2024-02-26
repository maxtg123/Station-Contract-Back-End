package com.contract.danhmuc.loaiphongmay.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.loaiphongmay.model.DmLoaiPhongMayModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_phong_may")
@Where(clause = "deleted_at IS NULL")
public class DmLoaiPhongMayEntity extends DmLoaiPhongMayModel {
  // @OneToMany(mappedBy = "dmLoaiPhongMayEntity", )
  // private Set<HopDongEntity> hopDongEntitySet = new HashSet<>();
}