package com.contract.danhmuc.phongdai.controller;

import java.util.List;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.thue.controller.DmThueController;
import com.contract.danhmuc.thue.model.DmThueModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.danhmuc.phongdai.entity.DmPhongDaiEntity;
import com.contract.danhmuc.phongdai.model.DmPhongDaiModel;
import com.contract.danhmuc.phongdai.service.DmPhongDaiService;
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
@Tag(name = "Danh mục - Phòng đài")
public class DmPhongDaiController extends BaseController<DmPhongDaiModel> {

    private class DmPhongDaiResponseClass extends ResponseModel<DmPhongDaiModel> {}

    @Autowired
    private DmPhongDaiService dmPhongDaiService;

    @Operation(summary = "Danh sách danh mục phòng đài",
        description = "Danh sách danh mục phòng đài")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Trả vể danh sách tất cả danh mục phòng đài",
        content = @Content(array = @ArraySchema(schema = @Schema(
            implementation = DmPhongDaiResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/phong-dai")
    public ResponseModel<DmPhongDaiModel> getAllPhongDai() throws Exception {
        List<DmPhongDaiEntity> listEntity = dmPhongDaiService.findAll();
        List<DmPhongDaiModel> listModel = dmPhongDaiService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại Phòng đài",
        description = "Thêm mới danh mục loại Phòng đài")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục loại Phòng đài vừa được tạo thành công",
        content = @Content(
            schema = @Schema(implementation = DmPhongDaiResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/phong-dai")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmPhongDaiModel> create(@RequestBody DmPhongDaiModel phongDaiModel) {
        return super.success(dmPhongDaiService.save(phongDaiModel));
    }

    @Operation(summary = "Cập nhật danh mục loại Phòng đài",
        description = "Cập nhật danh mục loại Phòng đài")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục loại Phòng đài vừa được cập nhật thành công",
        content = @Content(
            schema = @Schema(implementation = DmPhongDaiResponseClass.class))),
        @ApiResponse(responseCode = "400",
            description = "Mã hoặc tên Phòng đài không được để trống",
            content = @Content(schema = @Schema(
                implementation = DmPhongDaiResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/phong-dai/{id}")
    public ResponseModel<DmPhongDaiModel> update(@RequestBody DmPhongDaiModel phongDaiModel,
                                  @PathVariable("id") int id) {
        phongDaiModel.setId(id);
        return super.success(dmPhongDaiService.update(phongDaiModel));
    }

    @Operation(summary = "Xóa danh mục phòng đài", description = "Xóa danh mục phòng đài")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục phòng đài vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmPhongDaiResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/phong-dai/{id}")
    public ResponseModel<DmPhongDaiModel> delete(@PathVariable("id") int id) {
        try {
            dmPhongDaiService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}