package com.contract.hopdong.hopdong.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class HopDongExportDto {
  private String listKey;
  private String excludeKey;
  private String listId;
}
