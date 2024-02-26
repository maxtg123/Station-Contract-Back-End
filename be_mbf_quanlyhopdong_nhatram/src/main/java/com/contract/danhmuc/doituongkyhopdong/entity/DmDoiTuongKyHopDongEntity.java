package com.contract.danhmuc.doituongkyhopdong.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.doituongkyhopdong.model.DmDoiTuongKyHopDongModel;
import com.contract.hopdong.hopdong.entity.HopDongEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_doi_tuong_ky_hop_dong")
@Where(clause = "deleted_at IS NULL")
public class DmDoiTuongKyHopDongEntity extends DmDoiTuongKyHopDongModel {
    @OneToMany(mappedBy = "dmDoiTuongKyHopDongEntity")
    private Set<HopDongEntity> hopDongEntitySet = new HashSet<>();
}
