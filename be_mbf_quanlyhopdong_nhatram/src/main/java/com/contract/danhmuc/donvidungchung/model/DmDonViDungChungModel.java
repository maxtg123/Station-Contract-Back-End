package com.contract.danhmuc.donvidungchung.model;

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

import com.contract.hopdong.hopdongtram_dungchung.entity.HopDongTramDungChungEntity;
import com.contract.utils.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Table(name = "dm_dung_chung")
@Where(clause = "deleted_at IS NULL")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class DmDonViDungChungModel extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "ma_datasite", nullable = true)
    private String maDataSite;

    @Column(name = "ten", nullable = false)
    @NotBlank
    @NotEmpty
    @NotNull(message = "Tên không được trống")
    private String ten;

    @Column(name = "ma", nullable = true)
    private String ma;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @OneToMany(mappedBy = "dmDonViDungChungEntity", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<HopDongTramDungChungEntity> hopDongTramDungChungEntities = new HashSet<>();
}
