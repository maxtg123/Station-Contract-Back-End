package com.contract.danhmuc.loaithietbiran.controller;

import java.util.List;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.hinhthucdautu.controller.DmHinhThucDauTuController;
import com.contract.danhmuc.hinhthucdautu.model.DmHinhThucDauTuModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.danhmuc.loaithietbiran.entity.DmLoaiThietBiRanEntity;
import com.contract.danhmuc.loaithietbiran.model.DmLoaiThietBiRanModel;
import com.contract.danhmuc.loaithietbiran.service.DmLoaiThietBiRanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Danh mục - Loại thiết bị ran")
public class DmLoaiThietBiRanController extends BaseController<DmLoaiThietBiRanModel> {

    private class DmLoaiThietBiRanResponseClass extends ResponseModel<DmLoaiThietBiRanModel> {}

    @Autowired
    private DmLoaiThietBiRanService dmLoaiThietBiRanService;

    @Operation(summary = "Danh sách danh mục loại thiết bị ran",
        description = "Danh sách danh mục loại thiết bị ran")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Trả vể danh sách tất cả danh mục loại thiết bị ran",
        content = @Content(array = @ArraySchema(schema = @Schema(
            implementation = DmLoaiThietBiRanResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/loai-thiet-bi-ran")
    public ResponseModel<DmLoaiThietBiRanModel> getAllLoaiThietBiRan() throws Exception {
        List<DmLoaiThietBiRanEntity> listEntity = dmLoaiThietBiRanService.findAll();
        List<DmLoaiThietBiRanModel> listModel = dmLoaiThietBiRanService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại Thiết bị ran",
        description = "Thêm mới danh mục loại Thiết bị ran")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục loại Thiết bị ran vừa được tạo thành công",
        content = @Content(schema = @Schema(
            implementation = DmLoaiThietBiRanResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/loai-thiet-bi-ran")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmLoaiThietBiRanModel> create(
        @RequestBody DmLoaiThietBiRanModel loaiThietBiRanModel) {
        return super.success(dmLoaiThietBiRanService.save(loaiThietBiRanModel));
    }

    @Operation(summary = "Cập nhật danh mục loại Thiết bị ran",
        description = "Cập nhật danh mục loại Thiết bị ran")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục loại Thiết bị ran vừa được cập nhật thành công",
        content = @Content(schema = @Schema(
            implementation = DmLoaiThietBiRanResponseClass.class))),
        @ApiResponse(responseCode = "400",
            description = "Mã hoặc tên Thiết bị ran không được để trống",
            content = @Content(schema = @Schema(
                implementation = DmLoaiThietBiRanResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/loai-thiet-bi-ran/{id}")
    public ResponseModel<DmLoaiThietBiRanModel> update(@RequestBody DmLoaiThietBiRanModel loaiThietBiRanModel,
                                        @PathVariable("id") int id) {
        loaiThietBiRanModel.setId(id);
        return super.success(dmLoaiThietBiRanService.update(loaiThietBiRanModel));
    }

    @Operation(summary = "Xóa danh mục Loại Thiết Bị RAN", description = "Xóa danh mục Loại Thiết Bị RAN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Danh mục Loại Thiết Bị RAN vừa được xóa thành công",
            content = @Content(schema = @Schema(implementation = DmLoaiThietBiRanController.DmLoaiThietBiRanResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/loai-thiet-bi-ran/{id}")
    public ResponseModel<DmLoaiThietBiRanModel> delete(@PathVariable("id") Integer id) {
        try {
            dmLoaiThietBiRanService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}
