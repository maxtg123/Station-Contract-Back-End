package com.contract.hopdong.hopdongpheduyet.model;

import java.util.List;

import lombok.Data;

@Data
public class HopDongPheDuyetCreateDto {
    private List<Long> hopDongIdList;
    private List<Long> nguoiPheDuyetIdList;
    private String ghiChu;
}
