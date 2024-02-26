package com.contract.hopdong.hopdongpheduyet.model;

import com.contract.hopdong.hopdongfile.enums.LoaiFileEnum;

import lombok.Data;

@Data
public class LoaiFileKemTheo {
    private Long hopDongId;
    private LoaiFileEnum loaiFile;
}
