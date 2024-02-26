package com.contract.hopdong.hopdongdamphan.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.hopdong.hopdongdamphan.model.DamPhanTienTrinhCreateDto;
import com.contract.hopdong.hopdongdamphan.model.HopDongDamPhanCreateDto;
import com.contract.hopdong.hopdongdamphan.model.HopDongDamPhanModel;
import com.contract.hopdong.hopdongdamphan.model.XetDuyetDamPhanTienTrinhDto;
import com.contract.hopdong.hopdongdamphan.service.HopDongDamPhanService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Hợp đồng đàm phán", description = "API quản lý hợp đồng đàm phán")
public class HopDongDamPhanController extends BaseController<HopDongDamPhanModel> {
  private class HopDongDamPhanResponseClass extends
      ResponseModel<HopDongDamPhanModel> {
  }

  @Autowired
  private HopDongDamPhanService hopDongDamPhanService;

  @Operation(summary = "Tạo mới hợp đồng đàm phán", description = "Tạo mới hợp đồng đàm phán")
  // TODO check permission
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Trả về hợp đồng đàm phán mới tạo", content = @Content(schema = @Schema(implementation = HopDongDamPhanResponseClass.class))) })
  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "/hop-dong-dam-phan")
  public ResponseModel<HopDongDamPhanModel> create(
      @RequestBody HopDongDamPhanCreateDto hopDongDamPhanCreateDto) throws Exception {
    List<HopDongDamPhanModel> listSaved = hopDongDamPhanService.create(hopDongDamPhanCreateDto);
    return super.success(listSaved, 1, 0, listSaved.size(), (long) listSaved.size());
  }

  @Operation(summary = "Tạo mới tiến trình đàm phán", description = "Tạo mới tiến trình đàm phán")
  // TODO check permission
  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "hop-dong/{hopDongId}/hop-dong-dam-phan/{hopDongDamPhanId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseModel<HopDongDamPhanModel> createTienTrinh(@PathVariable("hopDongId") Long hopDongId,
      @PathVariable("hopDongDamPhanId") Long hopDongDamPhanId,
      @RequestParam(value = "files", required = false) MultipartFile[] files,
      @RequestParam(value = "input", required = true) String input) throws Exception {
    DamPhanTienTrinhCreateDto createDto = new ObjectMapper().readValue(input, DamPhanTienTrinhCreateDto.class);
    HopDongDamPhanModel result = hopDongDamPhanService.createNoiDungDamPhan(hopDongId, hopDongDamPhanId, createDto,
        files);
    return super.success(result);
  }

  @Operation(summary = "Xét duyệt tiến trình đàm phán", description = "Xét duỵệt tiến trình đàm phán")
  // TODO check permission
  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "hop-dong/{hopDongId}/hop-dong-dam-phan/{damPhanId}/xet-duyet/{tienTrinhId}")
  public ResponseModel<HopDongDamPhanModel> xetDuyet(@PathVariable("hopDongId") Long hopDongId,
      @PathVariable("damPhanId") Long damPhanId, @PathVariable("tienTrinhId") Long tienTrinhId,
      @RequestBody XetDuyetDamPhanTienTrinhDto xetDuyetDamPhanTienTrinhDto)
      throws Exception {
    HopDongDamPhanModel result = hopDongDamPhanService.xetDuyetTienTrinhDamPhan(hopDongId, damPhanId, tienTrinhId,
        xetDuyetDamPhanTienTrinhDto);
    return super.success(result);
  }

  @Operation(summary = "Danh sách đàm phán của hợp đồng", description = "Danh sách danh đàm phán của hợp đồng")
  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "/hop-dong/{id}/list-dam-phan")
  public ResponseModel<HopDongDamPhanModel> getAllListDamPhanOfHopDong(@PathVariable("id") Long hopDongId) {
    List<HopDongDamPhanModel> result = hopDongDamPhanService.getAllByHopDong(hopDongId).stream()
        .map(_entity -> HopDongDamPhanModel.fromEntity(_entity, true)).collect(Collectors.toList());
    return super.success(result, 0, 0, 0, (long) result.size());
  }

}
