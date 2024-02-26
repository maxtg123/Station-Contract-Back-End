package com.contract.old.hopdong_phuluc.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.contract.old.hopdong_phuluc.model.OldHopDongPhuLucModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "old_hop_dong_phu_luc")
public class OldHopDongPhuLucEntity extends OldHopDongPhuLucModel {

}
