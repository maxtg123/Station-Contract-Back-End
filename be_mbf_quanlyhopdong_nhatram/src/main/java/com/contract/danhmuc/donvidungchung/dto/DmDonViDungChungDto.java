package com.contract.danhmuc.donvidungchung.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@Data
@AutoConfiguration
@NoArgsConstructor
public class DmDonViDungChungDto {

    private String maDataSite;
    private String ten;
    private String ma;
    private String ghiChu;
}
