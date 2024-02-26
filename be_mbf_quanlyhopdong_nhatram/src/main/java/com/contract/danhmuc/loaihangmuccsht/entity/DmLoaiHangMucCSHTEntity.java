package com.contract.danhmuc.loaihangmuccsht.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.danhmuc.loaihangmuccsht.model.DmLoaiHangMucCSHTModel;
import com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_hang_muc_csht")
@Where(clause = "deleted_at IS NULL")
public class DmLoaiHangMucCSHTEntity extends DmLoaiHangMucCSHTModel {
    @OneToMany(mappedBy = "dmLoaiHangMucCSHTEntity")
    private Set<HopDongTramDungChungEntity> hopDongTramDungChungList = new HashSet<>();
}
