package com.contract.hopdong.hopdongpheduyet_nguoinhan.controller;
// package com.contract.hopdong.hopdongpheduyet_nguoidung.controller;

// import com.contract.base.controller.BaseController;
// import com.contract.base.model.ResponseModel;
// import com.contract.common.annotation.ApiPrefixController;
// import
// com.contract.hopdong.hopdongpheduyet_nguoidung.entity.HopDongPheDuyetNguoiDungEntity;
// import
// com.contract.hopdong.hopdongpheduyet_nguoidung.model.HopDongPheDuyetNguoiDungModel;
// import
// com.contract.hopdong.hopdongpheduyet_nguoidung.service.HopDongPheDuyetNguoiDungService;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.media.Content;
// import io.swagger.v3.oas.annotations.media.Schema;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.security.SecurityRequirement;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;

// @CrossOrigin
// @RestController
// @ApiPrefixController
// @Tag(name = "Hợp đồng phê duyệt người dùng", description = "API quản lý hợp
// đồng phê duyệt người dùng")
// public class HopDongPheDuyetNguoiDungController extends
// BaseController<HopDongPheDuyetNguoiDungModel> {

// private class HopDongPheDuyetNguoiDungResponseClass extends
// ResponseModel<HopDongPheDuyetNguoiDungModel> {
// }

// @Autowired
// private HopDongPheDuyetNguoiDungService hopDongPheDuyetNguoiDungService;

// @Operation(summary = "Cập nhật danh sách hợp đồng phê duyệt người dùng",
// description = "Cập nhật danh sách hợp đồng phê duyệt người dùng")
// @ApiResponses(
// value = {@ApiResponse(responseCode = "200", description = "Trả về danh sách
// hợp đồng được phê duyệt người dùng",
// content = @Content(schema = @Schema(implementation =
// HopDongPheDuyetNguoiDungResponseClass.class)))})
// @SecurityRequirement(name = "Bearer Authentication")
// @PutMapping(value = "/hop-dong-phe-duyet-nguoi-dung")
// public ResponseModel<HopDongPheDuyetNguoiDungModel> update(@RequestBody
// List<HopDongPheDuyetNguoiDungModel> hopDongPheDuyetNguoiDungModels) throws
// Exception {

// List<HopDongPheDuyetNguoiDungModel> listReturn =
// hopDongPheDuyetNguoiDungService.update(hopDongPheDuyetNguoiDungModels);
// return super.success(listReturn, 1, 1, listReturn.size(), (long)
// listReturn.size());
// }
// }
