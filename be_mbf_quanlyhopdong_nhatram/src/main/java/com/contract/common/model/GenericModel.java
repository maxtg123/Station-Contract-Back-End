package com.contract.common.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
@SuperBuilder
public class GenericModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id = 0L;

  public GenericModel() {
  }

  public GenericModel(GenericModel model) {
    if (model != null) {
      this.id = model.getId();
    } else {
      this.id = 0L;
    }
  }

  @Override
  public String toString() {
    return "GenericModel{" +
        "id=" + id +
        '}';
  }
}
