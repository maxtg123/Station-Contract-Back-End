package com.contract.process.entity;

import com.contract.process.model.ProcessModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "tien_trinh")
@Where(clause = "deleted_at IS NULL")
public class ProcessEntity extends ProcessModel {
}
