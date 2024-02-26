package com.contract.hopdong.hopdongnhatram.model;

import java.util.Date;

import lombok.Data;

@Data
public class ThanhToanHopDongRequest {
    private Long hopHongTramId;
    private Date ngayThanhToan;
}