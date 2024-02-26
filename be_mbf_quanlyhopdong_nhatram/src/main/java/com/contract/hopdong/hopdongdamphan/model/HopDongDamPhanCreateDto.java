package com.contract.hopdong.hopdongdamphan.model;

import java.util.Date;
import java.util.List;

import com.contract.hopdong.hopdongdamphan.enums.MucDoUuTienDamPhanEnum;

import lombok.Data;

@Data
public class HopDongDamPhanCreateDto {
    private List<Long> hopDongIdList;
    private List<Long> nguoiDamPhanIdList;
    private String ghiChu;
    private MucDoUuTienDamPhanEnum mucDoUuTien;
    private Date fromDate;
    private Date toDate;
}
