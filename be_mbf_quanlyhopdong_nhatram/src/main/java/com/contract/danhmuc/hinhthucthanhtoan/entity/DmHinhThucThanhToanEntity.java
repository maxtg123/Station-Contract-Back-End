package com.contract.danhmuc.hinhthucthanhtoan.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.hinhthucthanhtoan.model.DmHinhThucThanhToanModel;
import com.contract.hopdong.hopdong.entity.HopDongEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_hinh_thuc_thanh_toan")
@Where(clause = "deleted_at IS NULL")
public class DmHinhThucThanhToanEntity extends DmHinhThucThanhToanModel {
    @OneToMany(mappedBy = "dmHinhThucThanhToanEntity")
    private Set<HopDongEntity> hopDongEntitySet = new HashSet<>();
}
