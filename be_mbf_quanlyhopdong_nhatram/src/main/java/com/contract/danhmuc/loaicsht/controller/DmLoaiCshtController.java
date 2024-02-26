package com.contract.danhmuc.loaicsht.controller;

import java.util.List;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.exception.DeleteInUseDataException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.danhmuc.loaicsht.entity.DmLoaiCshtEntity;
import com.contract.danhmuc.loaicsht.model.DmLoaiCshtModel;
import com.contract.danhmuc.loaicsht.service.DmLoaiCshtService;
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
@Tag(name = "Danh mục - Loại CSHT")
public class DmLoaiCshtController extends BaseController<DmLoaiCshtModel> {

    private class DmLoaiCshtResponseClass extends ResponseModel<DmLoaiCshtModel> {}

    @Autowired
    private DmLoaiCshtService dmLoaiCshtService;

    @Operation(summary = "Danh sách danh mục loại CSHT",
            description = "Danh sách danh mục loại CSHT")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Trả vể danh sách tất cả danh mục loại CSHT",
            content = @Content(array = @ArraySchema(schema = @Schema(
                    implementation = DmLoaiCshtResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/loai-csht")
    public ResponseModel<DmLoaiCshtModel> getAllLoaiCsht() throws Exception {
        List<DmLoaiCshtEntity> listEntity = dmLoaiCshtService.findAll();
        List<DmLoaiCshtModel> listModel = dmLoaiCshtService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(), (long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại CSHT",
            description = "Thêm mới danh mục loại CSHT")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Danh mục loại CSHT vừa được tạo thành công",
            content = @Content(
                    schema = @Schema(implementation = DmLoaiCshtResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/loai-csht")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmLoaiCshtModel> create(@RequestBody DmLoaiCshtModel loaiCsht) {
        return super.success(dmLoaiCshtService.save(loaiCsht));
    }

    @Operation(summary = "Cập nhật danh mục loại CSHT",
            description = "Cập nhật danh mục loại CSHT")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Danh mục loại CSHT vừa được cập nhật thành công",
            content = @Content(
                    schema = @Schema(implementation = DmLoaiCshtResponseClass.class))),
            @ApiResponse(responseCode = "400",
                    description = "Mã CSHT hoặc Tên CSHT không được để trống",
                    content = @Content(schema = @Schema(
                            implementation = DmLoaiCshtModel.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/loai-csht/{id}")
    public ResponseModel<DmLoaiCshtModel> update(@RequestBody DmLoaiCshtModel dmLoaiCshtModel,
                                  @PathVariable("id") int id) {
        dmLoaiCshtModel.setId(id);
        return super.success(dmLoaiCshtService.update(id, dmLoaiCshtModel));
    }

    @Operation(summary = "Xóa danh mục loại CSHT", description = "Xóa danh mục loại CSHT")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Danh mục loại CSHT vừa được xóa thành công",
            content = @Content(
                    schema = @Schema(implementation = DmLoaiCshtResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/loai-csht/{id}")
    public ResponseModel<DmLoaiCshtModel> delete(@PathVariable("id") Integer id) {
        try {
            dmLoaiCshtService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }

    @Operation(summary = "Import danh mục loại CSHT",
            description = "create: import thêm mới <br> update: import cập nhật")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Danh mục loại CSHT vừa được import thành công",
            content = @Content(
                    schema = @Schema(implementation = DmLoaiCshtResponseClass.class))),
            @ApiResponse(responseCode = "400",
                    description = "Mã CSHT hoặc Tên CSHT không được để trống",
                    content = @Content(schema = @Schema(
                            implementation = DmLoaiCshtResponseClass.class)))})
    @Transactional // Nếu lỗi xảy ra thì annotation này sẽ rollback lại các dữ liệu đã import
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenImport(null)")
    @PostMapping(value = "/danh-muc/loai-csht/import/{loaiImport}")
    public ResponseModel<DmLoaiCshtModel> importDuLieu(@RequestBody List<DmLoaiCshtModel> loaiCshtModels,
                                              @PathVariable("loaiImport") String loaiImport) {
        List<DmLoaiCshtModel> listImported = dmLoaiCshtService.importDuLieu(loaiCshtModels, loaiImport);
        return super.success(listImported, 1, 0, listImported.size(),(long)listImported.size());
    }
}