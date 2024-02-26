package com.contract.danhmuc.khoanmuc.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.utils.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dm_khoan_muc")
@Where(clause = "deleted_at IS NULL")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class DmKhoanMucModel extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "ten", nullable = false)
    @NotBlank
    @NotEmpty
    @NotNull(message = "Tên không được trống")
    private String ten;

    @Column(name = "ma", nullable = true)
    @NotBlank
    @NotEmpty
    @NotNull(message = "Mã không được trống")
    private String ma;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @OneToMany(mappedBy = "dmKhoanMucEntity", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<HopDongEntity> hopDongEntitySet = new HashSet<>();
}
