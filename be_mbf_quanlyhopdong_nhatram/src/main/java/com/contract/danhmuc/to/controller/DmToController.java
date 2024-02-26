package com.contract.danhmuc.to.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.to.entity.DmToEntity;
import com.contract.danhmuc.to.model.DmToModel;
import com.contract.danhmuc.to.service.DmToService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@ApiPrefixController
@Tag(name = "Danh mục - Tổ")
@CrossOrigin
public class DmToController extends BaseController<DmToModel> {

    private class DmToResponseClass extends ResponseModel<DmToModel> {
    }

    @Autowired
    private DmToService dmToService;

    @Operation(summary = "Danh sách danh mục tổ", description = "Danh sách danh mục tổ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách tất cả danh mục tổ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DmToResponseClass.class)))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/to")
    public ResponseModel<DmToModel> getAllTo() throws Exception {
        List<DmToEntity> listEntity = dmToService.findAll();
        List<DmToModel> listModel = dmToService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(), (long) listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục Tổ", description = "Thêm mới danh mục Tổ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục tổ vừa được tạo thành công", content = @Content(schema = @Schema(implementation = DmToResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/to")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmToModel> create(@RequestBody DmToModel dmToModel) {
        return super.success(dmToService.save(dmToModel));
    }

    @Operation(summary = "Cập nhật danh mục Tổ", description = "Cập nhật danh mục Tổ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục Tổ vừa được cập nhật thành công", content = @Content(schema = @Schema(implementation = DmToResponseClass.class))),
            @ApiResponse(responseCode = "400", description = "Mã hoặc tên Tổ không được để trống", content = @Content(schema = @Schema(implementation = DmToResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/to/{id}")
    public ResponseModel<DmToModel> update(@RequestBody DmToModel dmToModel, @PathVariable("id") int id) {
        dmToModel.setId(id);
        return super.success(dmToService.update(dmToModel));
    }

    @Operation(summary = "Xóa danh mục tổ", description = "Xóa danh mục tổ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục tổ vừa được xóa thành công", content = @Content(schema = @Schema(implementation = DmToResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/to/{id}")
    public ResponseModel<DmToModel> delete(@PathVariable("id") int id) {
        try {
            dmToService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}
