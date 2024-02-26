package com.contract.hopdong.hopdongpheduyet.model;

public enum XetDuyetActionEnum {
  XET_DUYET_HOP_DONG("XET_DUYET_HOP_DONG"),
  XET_DUYET_PHU_LUC("XET_DUYET_PHU_LUC");

  private final String label;

  private XetDuyetActionEnum(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
