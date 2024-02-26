package com.contract.thongbao.model;

public enum LoaiThongBaoEnum {
  // hopdong - xetduyet
  XET_DUYET_CHO_PHE_DUYET("XET_DUYET_CHO_PHE_DUYET"),
  XET_DUYET_PHE_DUYET("XET_DUYET_PHE_DUYET"),
  XET_DUYET_TU_CHOI("XET_DUYET_TU_CHOI"),
  XET_DUYET_GUI_LAI("XET_DUYET_GUI_LAI"),
  // hopdong - damphan
  DAM_PHAN_GIAO_VIEC("DAM_PHAN_GIAO_VIEC"),
  DAM_PHAN_GUI_NOI_DUNG_DAM_PHAN("DAM_PHAN_GUI_NOI_DUNG_DAM_PHAN"),
  DAM_PHAN_TU_CHOI("DAM_PHAN_TU_CHOI"),
  DAM_PHAN_PHE_DUYET("DAM_PHAN_PHE_DUYET");

  private final String label;

  private LoaiThongBaoEnum(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
