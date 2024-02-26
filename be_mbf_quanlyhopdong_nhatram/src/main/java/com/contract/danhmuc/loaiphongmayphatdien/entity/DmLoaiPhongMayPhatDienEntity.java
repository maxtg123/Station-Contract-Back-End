package com.contract.danhmuc.loaiphongmayphatdien.entity;

import com.contract.danhmuc.loaiphongmayphatdien.model.DmLoaiPhongMayPhatDienModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_phong_may_phat_dien")
@Where(clause = "deleted_at IS NULL")
public class DmLoaiPhongMayPhatDienEntity extends DmLoaiPhongMayPhatDienModel {
}
