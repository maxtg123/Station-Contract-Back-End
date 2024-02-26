package com.contract.log.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.contract.log.model.LogModel;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "nguoidung_action_logs")
@Where(clause = "deleted_at IS NULL")
public class LogEntity extends LogModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_dung_id", insertable = false, updatable = false)
    private NguoiDungEntity nguoiDungEntity;
}
