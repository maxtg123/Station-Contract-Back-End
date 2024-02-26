package com.contract.process.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import com.contract.common.entity.AuditEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class ProcessModel extends AuditEntity<String> {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "nguoi_dung_id")
    protected Long userId;
    @Column(name = "module")
    protected String module;
    @Column(name = "action")
    protected String action;
    @Column(name = "tong_so")
    protected Long tongSo;
    @Column(name = "hoan_thanh")
    protected Long hoanThanh = 0L;
    @Column(name = "ket_thuc")
    protected Boolean ketThuc = false;
    @Column(name = "loi")
    protected Boolean loi = false;
    @Column(name = "so_luong_loi")
    protected Long soLuongLoi = 0L;
    @Transient
    protected List<String> listError;
}
