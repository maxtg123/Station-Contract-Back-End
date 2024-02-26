package com.contract.process.enums;

import java.util.ArrayList;
import java.util.List;

public enum ProcessActionEnum {
  DONG_BO_PHU_LUC_TU_CHUONG_TRINH_CU("ĐỒng bộ phụ lục từ chương trình cũ"),
  DONG_BO_THU_HUONG_TU_CHUONG_TRINH_CU("ĐỒng bộ thụ hưởng từ chương trình cũ");

  public static ProcessActionEnum valueOfLabel(String label) {
    for (ProcessActionEnum e : values()) {
      if (e.label.equals(label)) {
        return e;
      }
    }
    return null;
  }

  public static List<String> getAllValue() {
    List<String> listValue = new ArrayList<>();
    for (ProcessActionEnum e : values()) {
      listValue.add(e.label);
    }
    return listValue;
  }

  private final String label;

  ProcessActionEnum(String label) {
    this.label = label;
  }
}
