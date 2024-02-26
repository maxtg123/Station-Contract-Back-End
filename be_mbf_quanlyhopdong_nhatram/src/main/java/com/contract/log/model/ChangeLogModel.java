package com.contract.log.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ChangeLogModel {
  private List<ChangeModel> changes;
  private ForModel forModel;
  // 'create'|'update'|'delete'
  private String type;
  private String version;
}
