package com.contract.danhmuc.tinh.controller;

import java.util.List;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.phongdai.controller.DmPhongDaiController;
import com.contract.danhmuc.phongdai.model.DmPhongDaiModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.danhmuc.tinh.entity.DmTinhEntity;
import com.contract.danhmuc.tinh.model.DmTinhModel;
import com.contract.danhmuc.tinh.service.DmTinhService;
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
@Tag(name = "Danh mục - Tỉnh")
public class DmTinhController extends BaseController<DmTinhModel> {

    private class DmTinhResponseClass extends ResponseModel<DmTinhModel> {}

    @Autowired
    private DmTinhService dmTinhService;

    @Operation(summary = "Danh sách danh mục tỉnh", description = "Danh sách danh mục tỉnh")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Trả vể danh sách tất cả danh mục tỉnh",
        content = @Content(array = @ArraySchema(
            schema = @Schema(implementation = DmTinhResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/tinh")
    public ResponseModel<DmTinhModel> getAllDanhMucTinh() throws Exception {
        List<DmTinhEntity> listEntity = dmTinhService.findAll();
        List<DmTinhModel> listModel = dmTinhService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục Tỉnh", description = "Thêm mới danh mục Tỉnh")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục Tỉnh vừa được tạo thành công",
        content = @Content(schema = @Schema(implementation = DmTinhResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/tinh")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmTinhModel> create(@RequestBody DmTinhModel dmTinhModel) {
        return super.success(dmTinhService.save(dmTinhModel));
    }

    @Operation(summary = "Cập nhật danh mục Tỉnh", description = "Cập nhật danh mục Tỉnh")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Danh mục Tỉnh vừa được cập nhật thành công",
            content = @Content(schema = @Schema(
                implementation = DmTinhResponseClass.class))),
        @ApiResponse(responseCode = "400",
            description = "Mã hoặc tên Tỉnh không được để trống",
            content = @Content(schema = @Schema(
                implementation = DmTinhResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/tinh/{id}")
    public ResponseModel<DmTinhModel> update(@RequestBody DmTinhModel dmTinhModel,
                              @PathVariable("id") int id) {
        dmTinhModel.setId(id);
        return super.success(dmTinhService.update(dmTinhModel));
    }

    @Operation(summary = "Xóa danh mục tỉnh", description = "Xóa danh mục tỉnh")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục tỉnh vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmTinhResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/tinh/{id}")
    public ResponseModel<DmTinhModel> delete(@PathVariable("id") int id) {
        try {
            dmTinhService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}
