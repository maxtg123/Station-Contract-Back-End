package com.contract.chucvu.phanquyen.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Where;

import com.contract.chucvu.chucvu.entity.ChucVuEntity;
import com.contract.chucvu.phanquyen.model.ChucVuPhanQuyenModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "pd_chucvu_phanquyen", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "module", "action", "pd_chucvu_id" }) })
@Where(clause = "deleted_at IS NULL")
public class ChucVuPhanQuyenEntity extends ChucVuPhanQuyenModel {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pd_chucvu_id", insertable = false, updatable = false)
    private ChucVuEntity chucVuEntity;
}
