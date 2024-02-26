package com.contract.danhmuc.tramkhuvuc.controller;

import java.util.List;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.to.controller.DmToController;
import com.contract.danhmuc.to.model.DmToModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.danhmuc.tramkhuvuc.entity.DmTramKhuVucEntity;
import com.contract.danhmuc.tramkhuvuc.model.DmTramKhuVucModel;
import com.contract.danhmuc.tramkhuvuc.service.DmTramKhuVucService;
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
@Tag(name = "Danh mục - Trạm khu vực")
public class DmTramKhuVucController extends BaseController<DmTramKhuVucModel> {

    private class DmTramKhuVucResponseClass extends ResponseModel<DmTramKhuVucModel> {}

    @Autowired
    private DmTramKhuVucService dmTramKhuVucService;

    @Operation(summary = "Danh sách danh mục trạm khu vực",
        description = "Danh sách danh mục trạm khu vực")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Trả vể danh sách tất cả danh mục trạm khu vực",
        content = @Content(array = @ArraySchema(schema = @Schema(
            implementation = DmTramKhuVucResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/tram-khu-vuc")
    public ResponseModel<DmTramKhuVucModel> getAllTramKhuVuc() throws Exception{
        List<DmTramKhuVucEntity> listEntity = dmTramKhuVucService.findAll();
        List<DmTramKhuVucModel> listModel = dmTramKhuVucService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại Trạm khu vực",
        description = "Thêm mới danh mục loại Trạm khu vực")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục loại Trạm khu vực vừa được tạo thành công",
        content = @Content(schema = @Schema(
            implementation = DmTramKhuVucResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/tram-khu-vuc")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmTramKhuVucModel> create(@RequestBody DmTramKhuVucModel tramKhuVucModel) {
        return super.success(dmTramKhuVucService.save(tramKhuVucModel));
    }

    @Operation(summary = "Cập nhật danh mục loại Trạm khu vực",
        description = "Cập nhật danh mục loại Trạm khu vực")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục loại Trạm khu vực vừa được cập nhật thành công",
        content = @Content(schema = @Schema(
            implementation = DmTramKhuVucResponseClass.class))),
        @ApiResponse(responseCode = "400",
            description = "Mã CSHT hoặc Tên Trạm khu vực không được để trống",
            content = @Content(schema = @Schema(
                implementation = DmTramKhuVucResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/tram-khu-vuc/{id}")
    public ResponseModel<DmTramKhuVucModel> update(@RequestBody DmTramKhuVucModel tramKhuVucModel,
                                    @PathVariable("id") int id) {
        tramKhuVucModel.setId(id);
        return super.success(dmTramKhuVucService.update(tramKhuVucModel));
    }

    @Operation(summary = "Xóa danh mục trạm khu vực", description = "Xóa danh mục trạm khu vực")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục trạm khu vực vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmTramKhuVucResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/tram-khu-vuc/{id}")
    public ResponseModel<DmTramKhuVucModel> delete(@PathVariable("id") int id) {
        try {
            dmTramKhuVucService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}