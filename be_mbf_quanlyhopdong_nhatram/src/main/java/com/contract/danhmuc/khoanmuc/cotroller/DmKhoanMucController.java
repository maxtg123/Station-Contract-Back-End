package com.contract.danhmuc.khoanmuc.cotroller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.hinhthucdautu.model.DmHinhThucDauTuModel;
import com.contract.danhmuc.khoanmuc.dto.DmKhoanMucDto;
import com.contract.danhmuc.khoanmuc.model.DmKhoanMucModel;
import com.contract.danhmuc.khoanmuc.repository.DmKhoanMucRepository;
import com.contract.danhmuc.khoanmuc.service.DmKhoanMucService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Danh mục - Khoản mục")
public class DmKhoanMucController extends BaseController<DmKhoanMucModel> {
    @Autowired
    private DmKhoanMucService dmKhoanMucService;
    @Autowired
    private DmKhoanMucRepository dmKhoanMucRepository;

    private class DmKhoanMucResponseClass extends ResponseModel<DmKhoanMucModel> {
    }

    @Operation(summary = "Danh sách danh mục khoản mục", description = "Danh sách danh mục khoản mục")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách tất cả danh mục khoản mục",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = DmKhoanMucResponseClass.class))))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/khoan-muc")
    public ResponseModel<DmKhoanMucModel> getAllKhoanMuc() throws Exception {
        List<DmKhoanMucModel> listModel = dmKhoanMucService.findAll();
        return super.success(listModel, 1, 0, listModel == null ? 0 : listModel.size(), (long) listModel.size());
    }

    @Operation(summary = "Thêm mới danh mục Khoản mục", description = "Thêm mới danh mục Khoản mục")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục khoản mục vừa được tạo thành công",
                    content = @Content(schema = @Schema(implementation = DmKhoanMucResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/khoan-muc")
    public ResponseEntity<?> create(@RequestBody DmKhoanMucDto dmKhoanMucDto) {
        DmKhoanMucModel dmKhoanMucModel = dmKhoanMucService.saveDm(dmKhoanMucDto);
        if (dmKhoanMucModel != null) {
            return new ResponseEntity<DmKhoanMucModel>(dmKhoanMucModel, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Ten va Ma danh muc khong duoc rong!", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Cập nhật danh mục Khoản mục", description = "Cập nhật danh mục Khoản mục")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục Khoản mục vừa được cập nhật thành công",
                    content = @Content(schema = @Schema(implementation = DmKhoanMucResponseClass.class))),
            @ApiResponse(responseCode = "400", description = "Mã hoặc tên Khoản mục không được để trống",
                    content = @Content(schema = @Schema(implementation = DmKhoanMucModel.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/khoan-muc/{id}")
    public ResponseEntity<?> update(@RequestBody DmKhoanMucDto dmKhoanMucDto, @PathVariable("id") int id) {
        DmKhoanMucModel dmKhoanMucModel = dmKhoanMucRepository.findById(id);
        if (dmKhoanMucModel != null) {
            DmKhoanMucModel dmUpdate = dmKhoanMucService.update(id, dmKhoanMucDto);
            if (dmUpdate != null) {
                return new ResponseEntity<DmKhoanMucModel>(dmUpdate, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Ten va Ma danh muc khong duoc rong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Khong tim thay id danh muc này!", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Xóa danh mục Khoản mục", description = "Xóa danh mục Khoản mục")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh mục Khoản mục vừa được xóa thành công",
                    content = @Content(schema = @Schema(implementation = DmKhoanMucResponseClass.class)))})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/khoan-muc/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            boolean check = dmKhoanMucService.delete(id);
            if (check == true) {
                return new ResponseEntity<String>("Xoa thanh cong!", HttpStatus.OK);
            }
            return new ResponseEntity<String>("Khong tim thay id danh muc nay!", HttpStatus.BAD_REQUEST);
        } catch (DeleteInUseDataException e) {
            return new ResponseEntity<ResponseModel<DmKhoanMucModel>>(super.error(4001, "data in use"), HttpStatus.BAD_REQUEST);
        }
    }
}
