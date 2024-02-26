package com.contract.hopdong.hopdongpheduyet.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.contract.base.controller.BaseController;
// import com.contract.base.model.ResponseModel;
// import com.contract.common.annotation.ApiPrefixController;
// import com.contract.hopdong.hopdongpheduyet.dto.PheDuyetInputDto;
// import com.contract.hopdong.hopdongpheduyet.entity.HopDongPheDuyetEntity;
// import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetCreateModel;
// import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetModel;
// import com.contract.hopdong.hopdongpheduyet.model.TrangThaiPheDuyet;
// import com.contract.hopdong.hopdongpheduyet.service.HopDongPheDuyetService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.media.ArraySchema;
// import io.swagger.v3.oas.annotations.media.Content;
// import io.swagger.v3.oas.annotations.media.Schema;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.security.SecurityRequirement;
// import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.hopdong.hopdongpheduyet.dto.PheDuyetInputDto;
import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetCreateDto;
import com.contract.hopdong.hopdongpheduyet.model.HopDongPheDuyetModel;
import com.contract.hopdong.hopdongpheduyet.service.HopDongPheDuyetService;

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
@Tag(name = "Hợp đồng phê duyệt", description = "API quản lý hợp đồng phê duyệt")
public class HopDongPheDuyetController extends BaseController<HopDongPheDuyetModel> {

  private class HopDongPheDuyetResponseClass extends
      ResponseModel<HopDongPheDuyetModel> {
  }

  @Autowired
  private HopDongPheDuyetService hopDongPheDuyetService;

  // @Operation(summary = "Danh sách hợp đồng cần phê duyệt", description = "Danh
  // sách danh hợp đồng cần phê duyệt")
  // @ApiResponses(value = {
  // @ApiResponse(responseCode = "200", description = "Trả vể danh sách tất cả hợp
  // đồng cần phê duyệt", content = @Content(array = @ArraySchema(schema =
  // @Schema(implementation = HopDongPheDuyetResponseClass.class)))) })
  // @SecurityRequirement(name = "Bearer Authentication")
  // @GetMapping(value = "/hop-dong-phe-duyet")
  // public ResponseModel<HopDongPheDuyetModel> getDanhSachHopDongCanPheDuyet(
  // @RequestParam(value = "trangThaiPheDuyet", required = false)
  // List<TrangThaiPheDuyet> trangThaiPheDuyet,
  // @RequestParam(value = "hopDong", required = false) Long hopDongId,
  // @RequestParam(value = "page", required = false) Integer page,
  // @RequestParam(value = "size", required = false) Integer size) throws
  // Exception {
  // Page<HopDongPheDuyetEntity> hopDongPheDuyetModelList = hopDongPheDuyetService
  // .getDanhSachHopDongCanPheDuyet(hopDongId, trangThaiPheDuyet, page, size);
  // List<HopDongPheDuyetModel> listReturn = hopDongPheDuyetService
  // .convertEntityToModel(hopDongPheDuyetModelList.getContent());
  // return super.success(listReturn, hopDongPheDuyetModelList.getTotalPages(),
  // page != null ? page : 0,
  // size != null ? size : (int) hopDongPheDuyetModelList.getTotalElements(),
  // hopDongPheDuyetModelList.getTotalElements());
  // }

  @Operation(summary = "Tạo mới hợp đồng phê duyệt", description = "Tạo mới hợp đồng phê duyệt")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Trả về hợp đồng phê duyệt mới tạo", content = @Content(schema = @Schema(implementation = HopDongPheDuyetResponseClass.class))) })
  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "/hop-dong-phe-duyet")
  public ResponseModel<HopDongPheDuyetModel> create(
      @RequestBody HopDongPheDuyetCreateDto hopDongPheDuyetCreateModel) throws Exception {
    List<HopDongPheDuyetModel> listSaved = hopDongPheDuyetService.create(hopDongPheDuyetCreateModel);
    return super.success(listSaved, 1, 0, listSaved.size(), (long) listSaved.size());
  }

  @Operation(summary = "Danh sách phê duyệt của hợp đồng", description = "Danh sách danh phê duyệt của hợp đồng")
  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "/hop-dong/{id}/list-phe-duyet")
  public ResponseModel<HopDongPheDuyetModel> getAllListPheDuyetOfHopDong(@PathVariable("id") Long hopDongId) {
    List<HopDongPheDuyetModel> result = hopDongPheDuyetService.getAllByHopDong(hopDongId).stream()
        .map(_entity -> HopDongPheDuyetModel.fromEntity(_entity, true)).collect(Collectors.toList());
    return super.success(result, 0, 0, 0, (long) result.size());
  }

  // @Operation(summary = "Phê duyệt danh sách hợp đồng", description = "Phê duyệt
  // danh sách hợp đồng")
  // @ApiResponses(value = {
  // @ApiResponse(responseCode = "200", description = "Trả về danh sách hợp đồng
  // được phê duyệt", content = @Content(schema = @Schema(implementation =
  // HopDongPheDuyetResponseClass.class))) })
  // @SecurityRequirement(name = "Bearer Authentication")
  // @PutMapping(value = "/hop-dong-phe-duyet")
  // public ResponseModel<HopDongPheDuyetModel> update(@RequestBody
  // List<HopDongPheDuyetModel> hopDongPheDuyetModels)
  // throws Exception {
  // List<HopDongPheDuyetModel> hopDongPheDuyetModelList = hopDongPheDuyetService
  // .pheDuyetHopDong(hopDongPheDuyetModels);
  // return super.success(hopDongPheDuyetModelList, 1, 1,
  // hopDongPheDuyetModelList.size(),
  // (long) hopDongPheDuyetModelList.size());
  // }

  @Operation(summary = "Phê duyệt hợp đồng", description = "Đồng ý hoặc từ chối hợp đồng phê duyệt")
  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "/hop-dong-phe-duyet/xet-duyet")
  public ResponseModel<HopDongPheDuyetModel> xetDuyet(@RequestBody PheDuyetInputDto pheDuyetInputDto)
      throws Exception {
    HopDongPheDuyetModel model = hopDongPheDuyetService.xetDuyet(pheDuyetInputDto);
    return super.success(model);
  }
}
