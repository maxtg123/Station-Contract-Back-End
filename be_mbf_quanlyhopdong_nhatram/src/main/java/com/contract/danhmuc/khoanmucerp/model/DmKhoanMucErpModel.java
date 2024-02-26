package com.contract.danhmuc.khoanmucerp.model;

import com.contract.utils.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dm_khoan_muc_erp")
@Where(clause = "deleted_at IS NULL")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class DmKhoanMucErpModel extends DateAudit {
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
}
