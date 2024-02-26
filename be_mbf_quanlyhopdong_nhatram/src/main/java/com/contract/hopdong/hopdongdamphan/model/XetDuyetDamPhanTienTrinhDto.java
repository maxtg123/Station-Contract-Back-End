package com.contract.hopdong.hopdongdamphan.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class XetDuyetDamPhanTienTrinhDto {
  private String ghiChu;
  private String action; // 'tu_choi' | 'phe_duyet'
}
