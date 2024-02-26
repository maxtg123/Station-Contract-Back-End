package com.contract.common.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
@SuperBuilder()
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "id")
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BaseModel implements Serializable, Cloneable {

    public BaseModel() {
    }

}
