package com.contract.hopdong.hopdongdamphan.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class NoiDungThayDoiDamPhanDto {
    private String key;
    private String value;
    private Long tramId;
    private String ghiChu;
}
