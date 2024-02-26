// package com.contract.hopdong.hopdongnhatram.controller;

// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// import org.apache.commons.lang3.ObjectUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.contract.base.controller.BaseController;
// import com.contract.base.model.QueryResultModel;
// import com.contract.base.model.ResponseModel;
// import com.contract.common.annotation.ApiPrefixController;
// import com.contract.common.exception.InvalidDataException;
// import com.contract.common.exception.NotFoundException;
// import com.contract.hopdong.hopdong.model.HopDongModel;
// import com.contract.hopdong.hopdongfile.model.LoaiFile;
// import com.contract.hopdong.hopdongnhatram.entity.HopDongEntity;
// import com.contract.hopdong.hopdongnhatram.model.BangKeChiHoRequest;
// import
// com.contract.hopdong.hopdongnhatram.model.BangKeKhaiThanhToanTrongKyRequest;
// import com.contract.hopdong.hopdongnhatram.model.InfoFileUploadModel;
// import com.contract.hopdong.hopdongnhatram.model.ThanhToanHopDongRequest;
// import com.contract.hopdong.hopdongnhatram.model.TrangThaiHopDong;
// import com.contract.hopdong.hopdongnhatram.service.HopDongService;
// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.media.ArraySchema;
// import io.swagger.v3.oas.annotations.media.Content;
// import io.swagger.v3.oas.annotations.media.Schema;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.security.SecurityRequirement;
// import io.swagger.v3.oas.annotations.tags.Tag;

// @CrossOrigin
// @RestController
// @ApiPrefixController
// @Tag(name = "Hợp đồng", description = "API quản lý hợp đồng")
// public class HopDongController extends BaseController<HopDongModel> {

// private class HopDongResponseClass extends ResponseModel<HopDongModel> {
// }

// @Autowired
// private HopDongService hopDongService;

// @Operation(summary = "Lấy chi tiết hợp đồng", description = "Lấy chi tiết hợp
// đồng")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Trả về chi tiết hợp đồng",
// content = @Content(array = @ArraySchema(schema = @Schema(implementation =
// HopDongResponseClass.class)))),
// @ApiResponse(responseCode = "500", description = "Internal server error",
// content = @Content(schema = @Schema())), })
// @SecurityRequirement(name = "Bearer Authentication")
// @GetMapping(value = "/hop-dong/{id}")
// public ResponseModel<HopDongModel> getDetailHopDong(@PathVariable("id") Long
// id)
// throws Exception {
// HopDongEntity hopDongEntity = hopDongService.getDetail(id);
// if (ObjectUtils.isEmpty(hopDongEntity)) {
// throw new NotFoundException();
// }
// return super.success(hopDongService.getThuePhuTroHopDong(hopDongEntity));
// }

// @Operation(summary = "Lấy danh sách file", description = "Lấy danh sách
// file")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Lấy danh sách file",
// content = @Content(array = @ArraySchema(schema = @Schema(implementation =
// HopDongResponseClass.class)))),
// @ApiResponse(responseCode = "500", description = "Internal server error",
// content = @Content(schema = @Schema())), })
// @SecurityRequirement(name = "Bearer Authentication")
// @GetMapping(value = "/hop-dong/{id}/files")
// public ResponseModel<HopDongModel> getFileHopDong(@PathVariable("id") Long
// id,
// @RequestParam(value = "from", required = false) @DateTimeFormat(iso =
// DateTimeFormat.ISO.DATE) Date from,
// @RequestParam(value = "to", required = false) @DateTimeFormat(iso =
// DateTimeFormat.ISO.DATE) Date to,
// @RequestParam(value = "loai", required = false) LoaiFile loai,
// @RequestParam(value = "search", required = false) String search)
// throws Exception {
// HopDongEntity hopDongEntity = hopDongService.getDetail(id);
// if (ObjectUtils.isEmpty(hopDongEntity)) {
// throw new NotFoundException();
// }
// return super.success(hopDongService.getListFileHopDong(hopDongEntity, from,
// to,
// loai, search));
// }

// @Operation(summary = "Lấy danh sách hợp đồng", description = "Lấy danh sách
// hợp đồng")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Trả về danh sách hợp đồng",
// content = @Content(array = @ArraySchema(schema = @Schema(implementation =
// HopDongResponseClass.class)))),
// @ApiResponse(responseCode = "500", description = "Internal server error",
// content = @Content(schema = @Schema())), })
// @SecurityRequirement(name = "Bearer Authentication")
// @GetMapping(value = "/hop-dong")
// public ResponseModel<HopDongModel> getAllHopDong(
// @RequestParam(name = "responseType", defaultValue = "0") Integer
// responseType,
// @RequestParam(name = "size", defaultValue = "10") Integer size,
// @RequestParam(name = "page", defaultValue = "0") Integer page,
// @RequestParam(name = "search", required = false) String search,
// @RequestParam(name = "maTram", required = false) String maTram,
// @RequestParam(name = "soHopDong", required = false) String soHopDong,
// @RequestParam(name = "soHopDongErp", required = false) String soHopDongErp,
// @RequestParam(name = "ngayKyFrom", required = false) @Parameter(description =
// "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKyFrom,
// @RequestParam(name = "ngayKyTo", required = false) @Parameter(description =
// "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKyTo,
// @RequestParam(name = "ngayKetThucFrom", required = false)
// @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso =
// DateTimeFormat.ISO.DATE) Date ngayKetThucFrom,
// @RequestParam(name = "ngayKetThucTo", required = false)
// @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso =
// DateTimeFormat.ISO.DATE) Date ngayKetThucTo,
// @RequestParam(name = "hinhThucDauTuId", required = false) Integer
// hinhThucDauTuId,
// @RequestParam(name = "hinhThucKyHopDongId", required = false) Integer
// hinhThucKyHopDongId,
// @RequestParam(name = "doiTuongKyHopDongId", required = false) Integer
// doiTuongKyHopDongId,
// @RequestParam(name = "trangThaiThanhToan", required = false)
// @Parameter(description = "CHUA_THANH_TOAN") String trangThaiThanhToan,
// @RequestParam(name = "tinhTrangThanhToan", required = false) String
// tinhTrangThanhToan,
// @RequestParam(name = "phongDaiId", required = false) Integer phongDaiId,
// @RequestParam(name = "trangThaiHopDong", required = false) TrangThaiHopDong
// trangThaiHopDong)
// throws Exception {
// QueryResultModel<HopDongEntity> hopDongEntityPage =
// hopDongService.findAll(responseType, maTram, size,
// page,
// search, soHopDong, soHopDongErp, ngayKyFrom, ngayKyTo,
// ngayKetThucFrom, ngayKetThucTo, hinhThucDauTuId,
// hinhThucKyHopDongId, doiTuongKyHopDongId, trangThaiThanhToan,
// tinhTrangThanhToan,
// phongDaiId, trangThaiHopDong);
// List<HopDongModel> listReturn = null;
// if (responseType.equals(Integer.valueOf(1))) {
// // get all
// listReturn = hopDongEntityPage.getContent().stream()
// .map(model -> HopDongModel.fromEntity(model, false))
// .collect(Collectors.toList());
// return super.success(listReturn, hopDongEntityPage.getTotalPages(), page,
// size,
// hopDongEntityPage.getTotalElements());
// }
// listReturn =
// hopDongService.convertListEntityToModel(hopDongEntityPage.getContent());
// if (trangThaiThanhToan != null &&
// trangThaiThanhToan.equals("CHUA_THANH_TOAN")) {
// listReturn = hopDongService.getHopDongByTinhTrangThanhToan(listReturn,
// tinhTrangThanhToan);
// return super.success(listReturn, hopDongEntityPage.getTotalPages(), 0,
// listReturn.size(),
// (long) listReturn.size());
// }
// return super.success(listReturn, hopDongEntityPage.getTotalPages(), page,
// size,
// hopDongEntityPage.getTotalElements());
// }

// @Operation(summary = "Tạo mới hợp đồng", description = "Tạo mới hợp đồng")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Trả về hợp đồng mới tạo",
// content = @Content(schema = @Schema(implementation =
// HopDongResponseClass.class))) })
// @SecurityRequirement(name = "Bearer Authentication")
// @PreAuthorize("@kiemTraQuyenModuleHopDong.kiemTraQuyenThemMoi(#hopDong)")
// @PostMapping(value = "/hop-dong", consumes =
// MediaType.MULTIPART_FORM_DATA_VALUE)
// public ResponseModel<HopDongModel> create(
// @RequestParam(value = "hopDong") String hopDong,
// @RequestParam(value = "files", required = false) MultipartFile[] files,
// @RequestParam(value = "loaiFiles", required = false) String loaiFiles) throws
// Exception {
// List<LoaiFile> listFileInfo = null;
// if (loaiFiles != null) {
// try {
// ObjectMapper mapperInfoFile = new ObjectMapper();
// List<String> listString = mapperInfoFile.readValue(loaiFiles,
// ArrayList.class);
// listFileInfo = listString.stream().map(s -> LoaiFile.valueOf(s))
// .collect(Collectors.toList());
// } catch (Exception e) {
// System.out.println(e);
// throw new InvalidDataException();
// }
// }
// return super.success(hopDongService.create(null, hopDong, files,
// listFileInfo));
// }

// @Operation(summary = "Cập nhật hợp đồng", description = "Cập nhật hợp đồng")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Trả về hợp đồng mới cập
// nhật", content = @Content(schema = @Schema(implementation =
// HopDongResponseClass.class))) })
// @SecurityRequirement(name = "Bearer Authentication")
// @PreAuthorize("@kiemTraQuyenModuleHopDong.kiemTraQuyenCapNhat(#hopDong,#id)")
// @PutMapping(value = "/hop-dong/{id}", consumes =
// MediaType.MULTIPART_FORM_DATA_VALUE)
// public ResponseModel<HopDongModel> update(
// @PathVariable("id") Long id,
// @RequestParam(value = "hopDong") String hopDong,
// @RequestParam(value = "files", required = false) MultipartFile[] files,
// @RequestParam(value = "loaiFiles", required = false) String loaiFiles) throws
// Exception {
// List<LoaiFile> listFileInfo = null;
// if (loaiFiles != null) {
// try {
// ObjectMapper mapperInfoFile = new ObjectMapper();
// List<String> listString = mapperInfoFile.readValue(loaiFiles,
// ArrayList.class);
// listFileInfo = listString.stream().map(s -> LoaiFile.valueOf(s))
// .collect(Collectors.toList());
// } catch (Exception e) {
// System.out.println(e);
// throw new InvalidDataException();
// }
// }
// return super.success(hopDongService.update(id, null, hopDong, files,
// listFileInfo));
// }

// @Operation(summary = "Thanh toán hợp đồng", description = "Thanh toán hợp
// đồng")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Trả về danh sách hợp đồng
// vừa thực hiện thanh toán", content = @Content(schema = @Schema(implementation
// = HopDongResponseClass.class))) })
// @SecurityRequirement(name = "Bearer Authentication")
// //
// @PreAuthorize("@kiemTraQuyenModuleHopDong.kiemTraQuyenCapNhat(#hopDong,#id)")
// @PutMapping(value = "/hop-dong/thanh-toan")
// public ResponseModel<HopDongModel> thanhToanHopDong(
// @RequestBody List<ThanhToanHopDongRequest> listIdHopDong) {
// try {
// List<HopDongModel> listHopDongDaThanhToan =
// hopDongService.thanhToanHopDong(listIdHopDong);
// return super.success(listHopDongDaThanhToan, 1, 0,
// listHopDongDaThanhToan.size(),
// (long) listHopDongDaThanhToan.size());
// } catch (NotFoundException e) {
// return super.error(404,
// "Hợp đồng được chọn không tồn tại hoặc đã được thanh toán!");
// }
// }

// @Operation(summary = "Thống kê hợp đồng", description = "Thống kê hợp đồng")
// @SecurityRequirement(name = "Bearer Authentication")
// @GetMapping(value = "/hop-dong/stats", produces =
// MediaType.APPLICATION_JSON_VALUE)
// public ResponseEntity<?> thongKeHopDong(
// @RequestParam(value = "phongDaiId", required = false) Integer phongDaiId,
// @RequestParam(name = "trangThaiHopDong", required = false) TrangThaiHopDong
// trangThaiHopDong) {
// return
// ResponseEntity.of(Optional.of(hopDongService.thongKeHopDong(phongDaiId,
// trangThaiHopDong)));
// }

// @Operation(summary = "Xuất template đề nghị chi hộ", description = "Xuất
// template đề nghị chi hộ")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Xuất template đề nghị chi
// hộ", content = @Content(array = @ArraySchema(schema = @Schema()))),
// @ApiResponse(responseCode = "400", description = "Chưa chọn hợp đồng cần
// xuất", content = @Content(array = @ArraySchema(schema = @Schema()))),
// @ApiResponse(responseCode = "500", description = "Internal server error",
// content = @Content(schema = @Schema())), })
// @SecurityRequirement(name = "Bearer Authentication")
// @PostMapping(value = "/hop-dong/export/de-nghi-chi-ho", produces =
// MediaType.APPLICATION_JSON_VALUE, consumes =
// MediaType.APPLICATION_JSON_VALUE)
// public ResponseEntity<?> exportTemplateDeNghiChiHo(@RequestBody
// BangKeChiHoRequest request) throws Exception {
// return hopDongService.exportTemplateDeNghiChiHo(request);
// }

// @Operation(summary = "Import hợp đồng", description = "Import hợp đồng")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Import hợp đồng", content =
// @Content(array = @ArraySchema(schema = @Schema()))),
// @ApiResponse(responseCode = "500", description = "Internal server error",
// content = @Content(schema = @Schema())), })
// @SecurityRequirement(name = "Bearer Authentication")
// @PostMapping(value = "/hop-dong/import", produces =
// MediaType.APPLICATION_JSON_VALUE, consumes =
// MediaType.APPLICATION_JSON_VALUE)
// public Long importHopDong(@RequestBody List<HopDongModel> request) throws
// Exception {
// return hopDongService.importHopDong(request);
// }

// @Operation(summary = "Xuất template Bảng kê khai thanh toán trong kỳ",
// description = "Xuất template Bảng kê khai thanh toán trong kỳ")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Xuất template Bảng kê khai
// thanh toán trong kỳ", content = @Content(array = @ArraySchema(schema =
// @Schema()))),
// @ApiResponse(responseCode = "400", description = "Chưa chọn hợp đồng cần
// xuất", content = @Content(array = @ArraySchema(schema = @Schema()))),
// @ApiResponse(responseCode = "500", description = "Internal server error",
// content = @Content(schema = @Schema())), })
// @SecurityRequirement(name = "Bearer Authentication")
// @PostMapping(value = "/hop-dong/export/bang-ke-khai-thanh-toan")
// public ResponseEntity<?> exportTemplateBangKeKhaiThanhToanTrongKy(
// @RequestBody BangKeKhaiThanhToanTrongKyRequest request) throws Exception {
// return hopDongService.exportTemplateBangKeKhaiThanhToanTrongKy(request);
// }

// @Operation(summary = "Xóa hợp đồng", description = "Xóa hợp đồng")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Xóa hợp đồng thành công",
// content = @Content(array = @ArraySchema(schema = @Schema()))), })
// @SecurityRequirement(name = "Bearer Authentication")
// @PreAuthorize("@kiemTraQuyenModuleHopDong.kiemTraQuyenXoa(#id)")
// @DeleteMapping(value = "/hop-dong/{id}")
// public ResponseModel<HopDongModel> delete(@PathVariable("id") Long id) throws
// Exception {
// return super.success(hopDongService.delete(id));
// }

// @Operation(summary = "Upload file hợp đồng", description = "Upload file hợp
// đồng")
// @ApiResponses(value = {
// @ApiResponse(responseCode = "200", description = "Upload file hợp đồng",
// content = @Content(array = @ArraySchema(schema = @Schema()))), })
// @SecurityRequirement(name = "Bearer Authentication")
// @PutMapping(value = "/hop-dong/files/upload", consumes =
// MediaType.MULTIPART_FORM_DATA_VALUE)
// public List<Integer> upload(@RequestParam("infos") String infos,
// @RequestParam("files") MultipartFile[] files)
// throws Exception {
// List<InfoFileUploadModel> infoList = new ArrayList<>();
// if (infos == null || files == null) {
// throw new InvalidDataException();
// }
// try {
// ObjectMapper mapperInfoFile = new ObjectMapper();
// infoList = mapperInfoFile.readValue(infos, new
// TypeReference<List<InfoFileUploadModel>>() {
// });
// } catch (Exception e) {
// System.out.println(e);
// throw new InvalidDataException();
// }
// return hopDongService.upload(infoList, files);
// }
// }
