package com.contract.old.hopdong_phuluc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contract.common.annotation.ApiPrefixController;
import com.contract.old.hopdong_phuluc.service.OldHopDongPhuLucService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@ApiPrefixController
@Tag(name = "Old hợp đồng - phụ lục")
@CrossOrigin
public class OldHopDongPhuLucController {
  @Autowired
  private OldHopDongPhuLucService oldHopDongPhuLucService;

  @Operation(summary = "Đồng bộ phụ lục từ chương trình cũ", description = "Đồng bộ phụ lục từ chương trình cũ")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("@kiemTraQuyenOnlyAdmin.check()")
  @PostMapping(value = "/old-hop-dong/sync-phu-luc")
  public Long syncPhuLuc() {
    return oldHopDongPhuLucService.syncPhuLuc();
  }

}
