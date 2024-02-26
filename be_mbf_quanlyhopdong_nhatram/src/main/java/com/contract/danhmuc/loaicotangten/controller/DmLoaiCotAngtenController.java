package com.contract.danhmuc.loaicotangten.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.loaicotangten.entity.DmLoaiCotAngtenEntity;
import com.contract.danhmuc.loaicotangten.model.DmLoaiCotAngtenModel;
import com.contract.danhmuc.loaicotangten.service.DmLoaiCotAngtenService;
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
@Tag(name = "Danh mục - Loại cột ăngten")
public class DmLoaiCotAngtenController extends BaseController<DmLoaiCotAngtenModel> {

    private class DmLoaiCotAngtenResponseClass extends ResponseModel<DmLoaiCotAngtenModel> {}

    @Autowired
    private DmLoaiCotAngtenService dmLoaiCotAngtenService;

    @Operation(summary = "Danh sách danh mục loại cột ăngten", description = "Danh sách danh mục loại cột ăngten")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách tất cả danh mục loại cột ăngten",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = DmLoaiCotAngtenResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/loai-cot-angten")
    public ResponseModel<DmLoaiCotAngtenModel> getAllLoaiCotAngten() throws Exception{
        List<DmLoaiCotAngtenEntity> listEntity = dmLoaiCotAngtenService.findAll();
        List<DmLoaiCotAngtenModel> listModel = dmLoaiCotAngtenService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1 , 0, listModel.size(), (long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại cột angten", description = "Thêm mới danh mục loại cột angten")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục loại cột angten vừa được tạo thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiCotAngtenResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/loai-cot-angten")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmLoaiCotAngtenModel> create(@RequestBody DmLoaiCotAngtenModel cotAngtenModel) {
        return super.success(dmLoaiCotAngtenService.save(cotAngtenModel));
    }

    @Operation(summary = "Cập nhật danh mục loại cột angten", description = "Cập nhật danh mục loại cột angten")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục loại cột angten vừa được cập nhật thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiCotAngtenResponseClass.class))),
            @ApiResponse(responseCode = "400", description = "Mã hoặc tên cột angten không được để trống",
                    content = @Content(schema = @Schema(implementation = DmLoaiCotAngtenResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/loai-cot-angten/{id}")
    public ResponseModel<DmLoaiCotAngtenModel> update(@RequestBody DmLoaiCotAngtenModel cotAngtenModel, @PathVariable("id") int id) {
        cotAngtenModel.setId(id);
        return super.success(dmLoaiCotAngtenService.update(cotAngtenModel));
    }

    @Operation(summary = "Xóa danh mục Loại cột Angten ", description = "Xóa danh mục Loại cột Angten")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục Loại cột Angten vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmLoaiCotAngtenResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/loai-cot-angten/{id}")
    public ResponseModel<DmLoaiCotAngtenModel> delete(@PathVariable("id") Integer id) {
        try {
            dmLoaiCotAngtenService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }

}
