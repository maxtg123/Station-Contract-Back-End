package com.contract.danhmuc.loaihopdongphutro.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.loaihopdongphutro.model.DmLoaiHopDongPhuTroModel;
import com.contract.hopdong.hopdongtram_phutro.entity.HopDongTramPhuTroEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_hop_dong_phu_tro")
@Where(clause = "deleted_at IS NULL")
public class DmLoaiHopDongPhuTroEntity extends DmLoaiHopDongPhuTroModel {
    @OneToMany(mappedBy = "dmLoaiHopDongPhuTroEntity")
    private Set<HopDongTramPhuTroEntity> hopDongTramPhuTroEntitySet = new HashSet<>();
}
