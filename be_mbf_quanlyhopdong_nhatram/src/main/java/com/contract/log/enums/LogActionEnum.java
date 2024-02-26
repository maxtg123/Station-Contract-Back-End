package com.contract.log.enums;

import java.util.ArrayList;
import java.util.List;

public enum LogActionEnum {
  CREATE("create"), UPDATE("update"), DELETE("delete"), IMPORT("import");

  public static LogActionEnum valueOfLabel(String label) {
    for (LogActionEnum e : values()) {
      if (e.label.equals(label)) {
        return e;
      }
    }
    return null;
  }

  public static List<String> getAllValue() {
    List<String> listValue = new ArrayList<>();
    for (LogActionEnum e : values()) {
      listValue.add(e.label);
    }
    return listValue;
  }

  private final String label;

  LogActionEnum(String label) {
    this.label = label;
  }
}
