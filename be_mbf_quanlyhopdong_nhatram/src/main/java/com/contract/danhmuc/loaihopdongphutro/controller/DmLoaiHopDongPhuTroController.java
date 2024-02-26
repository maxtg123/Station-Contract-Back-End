package com.contract.danhmuc.loaihopdongphutro.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.doituongkyhopdong.model.DmDoiTuongKyHopDongModel;
import com.contract.danhmuc.loaihopdongphutro.entity.DmLoaiHopDongPhuTroEntity;
import com.contract.danhmuc.loaihopdongphutro.model.DmLoaiHopDongPhuTroModel;
import com.contract.danhmuc.loaihopdongphutro.service.DmLoaiHopDongPhuTroService;
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
@Tag(name = "Danh mục - Loại hợp đồng phụ trợ")
public class DmLoaiHopDongPhuTroController extends BaseController<DmLoaiHopDongPhuTroModel> {

    private class DmLoaiHopDongPhuTroResponseClass extends ResponseModel<DmLoaiHopDongPhuTroModel> {}

    @Autowired
    private DmLoaiHopDongPhuTroService hopDongPhuTroService;

    @Operation(summary = "Danh sách danh mục loại Hợp đồng phụ trợ",
        description = "Danh sách danh mục loại Hợp đồng phụ trợ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Trả vể danh sách tất cả danh mục loại Hợp đồng phụ trợ",
        content = @Content(array = @ArraySchema(schema = @Schema(
            implementation = DmLoaiHopDongPhuTroResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/loai-hop-dong-phu-tro")
    public ResponseModel<DmLoaiHopDongPhuTroModel> getAllLoaiHopDongPhuTro() throws Exception {
        List<DmLoaiHopDongPhuTroEntity> listEntity = hopDongPhuTroService.findAll();
        List<DmLoaiHopDongPhuTroModel> listModel = hopDongPhuTroService.convertListEntityToModel(listEntity);
        return super.success(listModel, 1, 0, listModel.size(),(long)listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục loại Hợp đồng phụ trợ",
        description = "Thêm mới danh mục loại Hợp đồng phụ trợ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục loại Hợp đồng phụ trợ vừa được tạo thành công",
        content = @Content(schema = @Schema(
            implementation = DmLoaiHopDongPhuTroResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/loai-hop-dong-phu-tro")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<DmLoaiHopDongPhuTroModel> create(
        @RequestBody DmLoaiHopDongPhuTroModel hopDongPhuTroModel) {
        return super.success(hopDongPhuTroService.save(hopDongPhuTroModel));
    }

    @Operation(summary = "Cập nhật danh mục loại Hợp đồng phụ trợ",
        description = "Cập nhật danh mục loại Hợp đồng phụ trợ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
        description = "Danh mục loại Hợp đồng phụ trợ vừa được cập nhật thành công",
        content = @Content(schema = @Schema(
            implementation = DmLoaiHopDongPhuTroResponseClass.class))),
        @ApiResponse(responseCode = "400",
            description = "Mã hoặc tên Hợp đồng phụ trợ không được để trống",
            content = @Content(schema = @Schema(
                implementation = DmLoaiHopDongPhuTroResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/loai-hop-dong-phu-tro/{id}")
    public ResponseModel<DmLoaiHopDongPhuTroModel> update(@RequestBody DmLoaiHopDongPhuTroModel hopDongPhuTroModel,
                                           @PathVariable("id") int id) {
        hopDongPhuTroModel.setId(id);
        return super.success(hopDongPhuTroService.update(hopDongPhuTroModel));
    }

    @Operation(summary = "Xóa danh mục loại Hợp đồng phụ trợ", description = "Xóa danh mục loại Hợp đồng phụ trợ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Danh mục loại Hợp đồng phụ trợ vừa được xóa thành công",
            content = @Content(schema = @Schema(implementation = DmLoaiHopDongPhuTroResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/loai-hop-dong-phu-tro/{id}")
    public ResponseModel<DmLoaiHopDongPhuTroModel> delete(@PathVariable("id") int id) {
        try {
            hopDongPhuTroService.delete(id);
            return super.success(null);
        } catch (DeleteInUseDataException e) {
            return super.error(4001, "data in use");
        }
    }
}