package com.contract.utils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(allowGetters = true)
public abstract class DateAudit implements Serializable {

    @Column(name = "created_at", updatable = false, columnDefinition = "timestamp default current_timestamp")
    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @Column(name = "created_by", updatable = false, columnDefinition = "varchar(255) default 'System'")
    @CreatedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp")
    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;

    @Column(name = "updated_by", columnDefinition = "varchar(255) default 'System'")
    @LastModifiedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String updatedBy;

    @Column(name = "deleted_at", nullable = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date deletedAt;

    public DateAudit() {
        this.deletedAt = null;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        updatedAt = new Date();
        if (createdAt == null) {
            createdAt = new Date();
        }
    }
}
