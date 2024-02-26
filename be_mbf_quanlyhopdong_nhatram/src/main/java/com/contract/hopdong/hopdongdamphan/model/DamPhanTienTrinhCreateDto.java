package com.contract.hopdong.hopdongdamphan.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class DamPhanTienTrinhCreateDto {
    private String ghiChu;
    private List<NoiDungThayDoiDamPhanDto> noiDungThayDoiDamPhanDtos;
}
