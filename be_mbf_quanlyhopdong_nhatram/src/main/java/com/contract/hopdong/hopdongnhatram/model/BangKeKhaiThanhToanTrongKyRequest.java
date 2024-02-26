package com.contract.hopdong.hopdongnhatram.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BangKeKhaiThanhToanTrongKyRequest {
    List<Long> listHopDongTramId;
    Date kyThanhToan;
}
