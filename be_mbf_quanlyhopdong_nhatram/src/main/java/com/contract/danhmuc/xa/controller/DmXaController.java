package com.contract.danhmuc.xa.controller;

import java.util.List;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.danhmuc.xa.entity.DmXaEntity;
import com.contract.danhmuc.xa.model.DmXaModel;
import com.contract.danhmuc.xa.service.DmXaService;
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
@Tag(name = "Danh mục - Xã")
public class DmXaController extends BaseController<DmXaModel> {

    private class DmXaResponseClass extends ResponseModel<DmXaModel> {}

    @Autowired
    private DmXaService dmXaService;

    @Operation(summary = "Danh sách danh mục xã", description = "Danh sách danh mục xã")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Trả vể danh sách tất cả danh mục xã",
        content = @Content(array = @ArraySchema(
            schema = @Schema(implementation = DmXaResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/xa")
    public ResponseModel<DmXaModel> getAllXa() throws Exception {
        List<DmXaEntity> listEntity = dmXaService.findAll();
        List<DmXaModel> listModel = dmXaService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục xã", description = "Thêm mới danh mục xã")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục xã vừa được tạo thành công",
        content = @Content(schema = @Schema(implementation = DmXaResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/xa")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmXaModel> create(@RequestBody DmXaModel dmXaModel) {
        return super.success(dmXaService.save(dmXaModel));
    }

    @Operation(summary = "Cập nhật danh mục xã", description = "Cập nhật danh mục xã")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Danh mục xã vừa được cập nhật thành công",
            content = @Content(schema = @Schema(
                implementation = DmXaResponseClass.class))),
        @ApiResponse(responseCode = "400",
            description = "Mã hoặc tên xã không được để trống",
            content = @Content(schema = @Schema(
                implementation = DmXaResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/xa/{id}")
    public ResponseModel<DmXaModel> update(@RequestBody DmXaModel dmXaModel, @PathVariable("id") int id) {
        dmXaModel.setId(id);
        return super.success(dmXaService.update(dmXaModel));
    }

    @Operation(summary = "Danh sách danh mục xã thuộc huyện",
        description = "Danh sách danh mục xã thuộc huyện")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Trả vể danh sách danh mục xã thuộc huyện",
        content = @Content(array = @ArraySchema(
            schema = @Schema(implementation = DmXaResponseClass.class))))})
    @GetMapping(value = "/danh-muc/xa/{huyenId}")
    public ResponseModel<DmXaModel> getXaThuocHuyen(@PathVariable("huyenId") int huyenId) {
        List<DmXaEntity> listEntity = dmXaService.findByHuyenId(huyenId);
        List<DmXaModel> listModel = dmXaService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }
}
