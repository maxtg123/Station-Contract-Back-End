package com.contract.danhmuc.loaitramvhkt.entity;

import com.contract.danhmuc.loaitramvhkt.model.DmLoaiTramVHKTModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_tram_vhkt")
@Where(clause = "deleted_at IS NULL")
public class DmLoaiTramVHKTEntity extends DmLoaiTramVHKTModel {
}
