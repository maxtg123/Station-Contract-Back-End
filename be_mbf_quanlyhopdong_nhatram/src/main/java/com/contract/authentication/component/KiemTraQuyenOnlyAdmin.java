package com.contract.authentication.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@Component
public class KiemTraQuyenOnlyAdmin {
  @Autowired
  protected NguoiDungService nguoiDungService;

  public boolean check() {
    try {
      if (nguoiDungService.kiemTraNguoiDungHienTaiLaSuperAdmin()) {
        return true;
      }
      return false;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }
}
