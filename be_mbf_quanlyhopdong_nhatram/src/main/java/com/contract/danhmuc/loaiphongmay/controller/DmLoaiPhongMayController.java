package com.contract.danhmuc.loaiphongmay.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.loaiphongmay.entity.DmLoaiPhongMayEntity;
import com.contract.danhmuc.loaiphongmay.model.DmLoaiPhongMayModel;
import com.contract.danhmuc.loaiphongmay.service.DmLoaiPhongMayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Danh mục - Loại phòng máy")
public class DmLoaiPhongMayController extends BaseController<DmLoaiPhongMayModel> {

    private class DmLoaiPhongMayResponseClass extends ResponseModel<DmLoaiPhongMayModel> {}

    @Autowired
    private DmLoaiPhongMayService loaiPhongMayService;

    @Operation(summary = "Danh sách danh mục loại Phòng máy", description = "Danh sách danh mục loại Phòng máy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                description = "Trả vể danh sách tất cả danh mục loại Phòng máy",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = DmLoaiPhongMayResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/loai-phong-may")
    public ResponseModel<DmLoaiPhongMayModel> getAllLoaiPhongMay() throws Exception {
        List<DmLoaiPhongMayEntity> listEntity = loaiPhongMayService.findAll();
        List<DmLoaiPhongMayModel> listModel = loaiPhongMayService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại Phòng máy", description = "Thêm mới danh mục loại Phòng máy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Danh mục loại Phòng máy vừa được tạo thành công",
            content = @Content(schema = @Schema(implementation = DmLoaiPhongMayResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/loai-phong-may")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmLoaiPhongMayModel> create(@RequestBody DmLoaiPhongMayModel loaiPhongMayModel) {
        return super.success(loaiPhongMayService.save(loaiPhongMayModel));
    }

    @Operation(summary = "Cập nhật danh mục loại Phòng máy", description = "Cập nhật danh mục loại Phòng máy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                description = "Danh mục loại Phòng máy vừa được cập nhật thành công",
                content = @Content(schema = @Schema(implementation = DmLoaiPhongMayResponseClass.class))),
            @ApiResponse(responseCode = "400",
                description = "Mã hoặc tên loại Phòng máy không được để trống",
                content = @Content(schema = @Schema(implementation = DmLoaiPhongMayResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/loai-phong-may/{id}")
    public ResponseModel<DmLoaiPhongMayModel> update(@RequestBody DmLoaiPhongMayModel loaiPhongMayModel, @PathVariable("id") int id) {
        loaiPhongMayModel.setId(id);
        return super.success(loaiPhongMayService.update(loaiPhongMayModel));
    }

    @Operation(summary = "Xóa danh mục loại Phòng máy", description = "Xóa danh mục loại Phòng máy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục loại Phòng máy vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiPhongMayResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/loai-phong-may/{id}")
    public ResponseModel<DmLoaiPhongMayModel> delete(@PathVariable("id") int id) {
        try {
            loaiPhongMayService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}