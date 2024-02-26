package com.contract.hopdong.hopdongpheduyet.dto;

import com.contract.hopdong.hopdongpheduyet.enums.TrangThaiPheDuyetEnum;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Data
public class PheDuyetInputDto {

  private Long hopDongPheDuyetId;

  private String ghiChu;

  private TrangThaiPheDuyetEnum trangThaiPheDuyet;

  private Long hopDongId;

  private String changeLog;

}
