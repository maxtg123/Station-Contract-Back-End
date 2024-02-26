package com.contract.danhmuc.thue.entity;

import com.contract.danhmuc.thue.model.DmThueModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_thue")
@Where(clause="deleted_at IS NULL")
public class DmThueEntity extends DmThueModel {
}