package com.contract.old.hopdong_thuhuong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contract.common.annotation.ApiPrefixController;
import com.contract.old.hopdong_thuhuong.service.OldHopDongThuHuongService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@ApiPrefixController
@Tag(name = "Old hợp đồng - thụ hưởng")
@CrossOrigin
public class OldHopDongThuHuongController {
  @Autowired
  private OldHopDongThuHuongService oldHopDongThuHuongService;

  @Operation(summary = "Đồng bộ thụ hưởng từ chương trình cũ", description = "Đồng bộ phụ lục từ chương trình cũ")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("@kiemTraQuyenOnlyAdmin.check()")
  @PostMapping(value = "/old-hop-dong/sync-thu-huong")
  public Long syncThuHuong() {
    return oldHopDongThuHuongService.syncThuHuong();
  }
}
