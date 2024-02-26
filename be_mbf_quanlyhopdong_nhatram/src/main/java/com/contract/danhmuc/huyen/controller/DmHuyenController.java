package com.contract.danhmuc.huyen.controller;

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
import com.contract.danhmuc.huyen.entity.DmHuyenEntity;
import com.contract.danhmuc.huyen.model.DmHuyenModel;
import com.contract.danhmuc.huyen.service.DmHuyenService;
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
@Tag(name = "Danh mục - Huyện")
public class DmHuyenController extends BaseController<DmHuyenModel> {

    private class DmHuyenResponseClass extends ResponseModel<DmHuyenModel> {}

    @Autowired
    private DmHuyenService dmHuyenService;

    @Operation(summary = "Danh sách danh mục huyện", description = "Danh sách danh mục huyện")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Trả vể danh sách tất cả danh mục huyện",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = DmHuyenResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/huyen")
    public ResponseModel<DmHuyenModel> getAllHuyen() throws Exception{
        List<DmHuyenEntity> listEntity = dmHuyenService.findAll();
        List<DmHuyenModel> listModel = dmHuyenService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1 , 0, listModel.size(), (long) listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục Huyện", description = "Thêm mới danh mục Huyện")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Danh mục Huyện vừa được tạo thành công",
            content = @Content(schema = @Schema(implementation = DmHuyenResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/huyen")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmHuyenModel> create(@RequestBody DmHuyenModel dmHuyenModel) {
        return super.success(dmHuyenService.save(dmHuyenModel));
    }

    @Operation(summary = "Cập nhật danh mục Huyện", description = "Cập nhật danh mục Huyện")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Danh mục Huyện vừa được cập nhật thành công",
                    content = @Content(schema = @Schema(
                            implementation = DmHuyenResponseClass.class))),
            @ApiResponse(responseCode = "400",
                    description = "Mã hoặc tên Huyện không được để trống",
                    content = @Content(schema = @Schema(
                            implementation = DmHuyenResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/huyen/{id}")
    public ResponseModel<DmHuyenModel> update(@RequestBody DmHuyenModel dmHuyenModel,
                               @PathVariable("id") int id) {
        dmHuyenModel.setId(id);
        return super.success(dmHuyenService.update(dmHuyenModel));
    }

    @Operation(summary = "Danh sách danh mục huyện thuộc tỉnh",
            description = "Danh sách danh mục huyện thuộc tỉnh")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Trả vể danh sách danh mục huyện thuộc tỉnh",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = DmHuyenResponseClass.class))))})
    @GetMapping(value = "/danh-muc/huyen/{tinhId}")
    public ResponseModel<DmHuyenModel> getHuyenThuocTinh(@PathVariable("tinhId") int tinhId) throws Exception {
        List<DmHuyenEntity> listEntity = dmHuyenService.findByTinh(tinhId);
        List<DmHuyenModel> listModel = dmHuyenService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1 , 0, listModel.size(), (long)listModel.size());
    }
}
