package com.contract.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.javers.core.metamodel.annotation.DiffIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.contract.common.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@SuperBuilder(builderMethodName = "auditEntityBuilder")
public class AuditEntity<U> extends BaseModel {

        @Column(name = "created_at", updatable = false, columnDefinition = "timestamp default current_timestamp")
        @CreatedDate
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @DiffIgnore
        private Date createdAt;

        @Column(name = "created_by", updatable = false, columnDefinition = "varchar(255) default 'System'")
        @CreatedBy
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @DiffIgnore
        private U createdBy;

        @Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp")
        @LastModifiedDate
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @DiffIgnore
        private Date updatedAt;

        @Column(name = "updated_by", columnDefinition = "varchar(255) default 'System'")
        @LastModifiedBy
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @DiffIgnore
        private U updatedBy;

        @Column(name = "deleted_at", nullable = true)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @DiffIgnore
        private Date deletedAt;
}
