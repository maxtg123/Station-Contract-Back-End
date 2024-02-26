package com.contract.hopdong.lichsu.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.hopdong.lichsu.model.HopDongLichSuModel;
import com.contract.hopdong.lichsu.service.HopDongLichSuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Hợp đồng lịch sử", description = "API quản lý lịch sử của hợp đồng")
public class HopDongLichSuController extends BaseController<HopDongLichSuModel> {
  @Autowired
  private HopDongLichSuService hopDongLichSuService;

  @Operation(summary = "Lấy danh sách lịch sử của hợp đồng", description = "Lấy danh sách lịch sử một hợp đồng")
  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "/hop-dong/lich-su")
  public ResponseModel<HopDongLichSuModel> getLichSuHopDong(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size,
      @RequestParam(name = "hopDongId", required = false) Long hopDongId) {

    Page<HopDongLichSuModel> lichSuPage = hopDongLichSuService.findAll(page, size, hopDongId);
    List<HopDongLichSuModel> list = lichSuPage.getContent().stream()
        .map(_entity -> HopDongLichSuModel.fromEntity(_entity, true)).collect(Collectors.toList());

    return super.success(list, lichSuPage.getTotalPages(), page != null ? page : 0,
        size != null ? size : (int) lichSuPage.getTotalElements(), lichSuPage.getTotalElements());
  }
}
