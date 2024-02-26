package com.contract.process.enums;

import java.util.ArrayList;
import java.util.List;

public enum ProcessModuleEnum {
  TRAM("Trạm"),
  HOP_DONG("Hợp đồng");

  public static ProcessModuleEnum valueOfLabel(String label) {
    for (ProcessModuleEnum e : values()) {
      if (e.label.equals(label)) {
        return e;
      }
    }
    return null;
  }

  public static List<String> getAllValue() {
    List<String> listValue = new ArrayList<>();
    for (ProcessModuleEnum e : values()) {
      listValue.add(e.label);
    }
    return listValue;
  }

  private final String label;

  ProcessModuleEnum(String label) {
    this.label = label;
  }
}
