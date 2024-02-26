package com.contract.log.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Comment;

import com.contract.common.entity.AuditEntity;
import com.contract.log.entity.LogEntity;
import com.contract.log.enums.LogActionEnum;
import com.contract.nguoidung.nguoidung.model.NguoiDungModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class LogModel extends AuditEntity<String> {
    public static LogModel fromEntity(LogEntity logEntity, boolean containChild) {
        LogModel logModel = new LogModel();

        try {
            logModel.setCreatedAt(logEntity.getCreatedAt());

            logModel.setId(logEntity.getId());
            logModel.setNguoiDungId(logEntity.getNguoiDungId());
            logModel.setModule(logEntity.getModule());
            logModel.setAction(logEntity.getAction());
            logModel.setChangeLog(logEntity.getChangeLog());

            if (logEntity.getNguoiDungEntity() != null && containChild) {
                logModel.setNguoiDung(NguoiDungModel.fromEntity(logEntity.getNguoiDungEntity(), true));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return logModel;
    }

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "nguoi_dung_id")
    protected Long nguoiDungId;

    @Column(name = "module", nullable = false)
    protected String module;

    @Column(name = "action")
    protected LogActionEnum action;

    @Lob
    @Column(name = "change_log", nullable = true, columnDefinition = "CLOB")
    @Comment("Have format: { changes: [{path:[], oldValue: string, value: string, type: 'update'|'delete'}], forModel: {id: numher; name: string} type: 'create'|'update'|'delete', version: string }")
    protected String changeLog;

    @Column(name = "ghi_chu")
    protected String ghiChu;

    @Transient
    protected NguoiDungModel nguoiDung;
}
