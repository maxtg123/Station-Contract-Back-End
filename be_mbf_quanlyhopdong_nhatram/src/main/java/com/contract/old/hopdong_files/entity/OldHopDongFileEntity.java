package com.contract.old.hopdong_files.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.contract.old.hopdong_files.model.OldHopDongFileModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "old_hop_dong_files")
public class OldHopDongFileEntity extends OldHopDongFileModel {
}
