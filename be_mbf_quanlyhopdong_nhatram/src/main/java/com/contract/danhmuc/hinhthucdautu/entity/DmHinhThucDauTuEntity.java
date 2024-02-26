package com.contract.danhmuc.hinhthucdautu.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.hinhthucdautu.model.DmHinhThucDauTuModel;
import com.contract.hopdong.hopdong.entity.HopDongEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_hinh_thuc_dau_tu")
@Where(clause = "deleted_at IS NULL")
public class DmHinhThucDauTuEntity extends DmHinhThucDauTuModel {
    @OneToMany(mappedBy = "dmHinhThucDauTuEntity")
    private Set<HopDongEntity> hopDongEntitySet = new HashSet<>();
}
