package com.contract.old.hopdong_files.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.old.hopdong_files.model.OldHopDongFileModel;
import com.contract.old.hopdong_files.service.OldHopDongFileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Old File Hợp Đồng")
public class OldHopDongFileController extends BaseController<OldHopDongFileModel> {

  @Autowired
  private OldHopDongFileService oldHopDongFileService;

  @Operation(summary = "Lấy danh sách file cũ", description = "Import hợp đồng")
  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "/hop-dong/old-files")
  public ResponseModel<OldHopDongFileModel> getListOldFilesOfHopDong(
      @RequestParam(name = "soHopDong", required = true) String soHopDong) {
    List<OldHopDongFileModel> models = oldHopDongFileService.findAllBySoHopDong(soHopDong);
    return super.success(models, 0, 0, 0, models == null ? 0L : models.size());
  }
}
