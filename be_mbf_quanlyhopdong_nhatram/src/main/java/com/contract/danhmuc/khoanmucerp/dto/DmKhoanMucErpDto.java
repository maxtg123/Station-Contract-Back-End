package com.contract.danhmuc.khoanmucerp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@Data
@AutoConfiguration
@NoArgsConstructor
public class DmKhoanMucErpDto {
    private String ten;
    private String ma;
    private String ghiChu;
}
