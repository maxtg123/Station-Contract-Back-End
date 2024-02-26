package com.contract.hopdong.hopdong.model;

import com.contract.hopdong.hopdongfile.enums.LoaiFileEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class LoaiFileInputDto {
  LoaiFileEnum loaiFile;
  Long tramId;
}
