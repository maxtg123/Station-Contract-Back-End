package com.contract.danhmuc.thue.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.thue.entity.DmThueEntity;
import com.contract.danhmuc.thue.model.DmThueModel;
import com.contract.danhmuc.thue.service.DmThueService;
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
@Tag(name = "Danh mục - Thuế VAT")
public class DmThueController extends BaseController<DmThueModel> {

    private class DmThueResponseClass extends ResponseModel<DmThueModel> {}

    @Autowired
    private DmThueService dmThueService;

    @Operation(summary = "Danh sách danh mục Thuế", description = "Danh sách danh mục Thuế")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                description = "Trả vể danh sách tất cả danh mục Thuế",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = DmThueResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/thue")
    public ResponseModel<DmThueModel> getAllThue() throws Exception {
        List<DmThueEntity> listEntity = dmThueService.findAll();
        List<DmThueModel> listModel = dmThueService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục Thuế", description = "Thêm mới danh mục Thuế")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Danh mục Thuế vừa được tạo thành công",
            content = @Content(schema = @Schema(implementation = DmThueResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/thue")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmThueModel> create(@RequestBody DmThueModel dmThueModel) {
        return super.success(dmThueService.save(dmThueModel));
    }

    @Operation(summary = "Cập nhật danh mục Thuế", description = "Cập nhật danh mục Thuế")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                description = "Danh mục Thuế vừa được cập nhật thành công",
                content = @Content(schema = @Schema(implementation = DmThueResponseClass.class))),
            @ApiResponse(responseCode = "400",
                description = "Mã hoặc tên Thuế không được để trống",
                content = @Content(schema = @Schema(implementation = DmThueResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/thue/{id}")
    public ResponseModel<DmThueModel> update(@RequestBody DmThueModel dmThueModel, @PathVariable("id") int id) {
        dmThueModel.setId(id);
        return super.success(dmThueService.update(dmThueModel));
    }

    @Operation(summary = "Xóa danh mục Thuế", description = "Xóa danh mục Thuế")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục Thuế vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmThueResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/thue/{id}")
    public ResponseModel<DmThueModel> delete(@PathVariable("id") int id) {
        try {
            dmThueService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}