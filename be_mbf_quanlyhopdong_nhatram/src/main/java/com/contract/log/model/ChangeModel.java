package com.contract.log.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ChangeModel {
  private List<String> paths;
  private String oldValue;
  private String value;
  // update | delete
  private String type;
}
