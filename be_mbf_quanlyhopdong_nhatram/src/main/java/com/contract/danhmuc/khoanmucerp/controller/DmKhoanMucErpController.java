package com.contract.danhmuc.khoanmucerp.controller;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.DeleteInUseDataException;
import com.contract.danhmuc.khoanmuc.model.DmKhoanMucModel;
import com.contract.danhmuc.khoanmucerp.dto.DmKhoanMucErpDto;
import com.contract.danhmuc.khoanmucerp.model.DmKhoanMucErpModel;
import com.contract.danhmuc.khoanmucerp.repository.DmKhoanMucErpRepository;
import com.contract.danhmuc.khoanmucerp.service.DmKhoanMucErpService;
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
@Tag(name = "Khoản Mục Erp")
public class DmKhoanMucErpController extends BaseController<DmKhoanMucErpModel> {
    @Autowired
    private DmKhoanMucErpService khoanMucErpService;
    @Autowired
    private DmKhoanMucErpRepository dmKhoanMucErpRepository;

    private class KhoanMucErpResponseClass extends ResponseModel<DmKhoanMucErpModel> {
    }

    @Operation(summary = "Danh sách khoản mục erp ", description = "Danh sách khoản mục erp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả vể danh sách tất cả khoản mục erp", content = @Content(array = @ArraySchema(schema = @Schema(implementation = KhoanMucErpResponseClass.class)))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()")
    @GetMapping(value = "/danh-muc/khoan-muc-erp")
    public ResponseModel<DmKhoanMucErpModel> getAllKhoanMucErp() throws Exception {
        List<DmKhoanMucErpModel> listModel = khoanMucErpService.findAll();
        return super.success(listModel, 1, 0, listModel == null ? 0 : listModel.size(), (long) listModel.size());
    }

    @Operation(summary = "Thêm mới Khoản mục erp", description = "Thêm mới Khoản mục erp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Khoản mục erp vừa được tạo thành công", content = @Content(schema = @Schema(implementation = KhoanMucErpResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi(null)")
    @PostMapping(value = "/danh-muc/khoan-muc-erp")
    public ResponseEntity<?> create(@RequestBody DmKhoanMucErpDto dmKhoanMucErpDto) {
        DmKhoanMucErpModel dmKhoanMucErpModel = khoanMucErpService.saveKhoanMuc(dmKhoanMucErpDto);
        if (dmKhoanMucErpModel != null) {
            return new ResponseEntity<DmKhoanMucErpModel>(dmKhoanMucErpModel, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Ten va Ma khoan muc khong duoc rong!", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Cập nhật Khoản mục erp", description = "Cập nhật Khoản mục erp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Khoản mục vừa được cập nhật thành công", content = @Content(schema = @Schema(implementation = KhoanMucErpResponseClass.class))),
            @ApiResponse(responseCode = "400", description = "Mã hoặc tên Khoản mục không được để trống", content = @Content(schema = @Schema(implementation = DmKhoanMucErpModel.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat(null,null)")
    @PutMapping(value = "/danh-muc/khoan-muc-erp/{id}")
    public ResponseEntity<?> update(@RequestBody DmKhoanMucErpDto dmKhoanMucErpDto, @PathVariable("id") int id) {
        DmKhoanMucErpModel dmKhoanMucErpModel = dmKhoanMucErpRepository.findById(id);
        if (dmKhoanMucErpModel != null) {
            DmKhoanMucErpModel dmUpdate = khoanMucErpService.updateKhoanMuc(id, dmKhoanMucErpDto);
            if (dmUpdate != null) {
                return new ResponseEntity<DmKhoanMucErpModel>(dmUpdate, HttpStatus.OK);
            }
            return new ResponseEntity<String>("Ten va Ma danh muc khong duoc rong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Khong tim thay id danh muc này!", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Xóa danh mục Khoản mục", description = "Xóa danh mục Khoản mục")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Khoản mục vừa được xóa thành công", content = @Content(schema = @Schema(implementation = KhoanMucErpResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa(null)")
    @DeleteMapping(value = "/danh-muc/khoan-muc-erp/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            boolean check = khoanMucErpService.delete(id);
            if (check == true) {
                return new ResponseEntity<String>("Xoa thanh cong!", HttpStatus.OK);
            }
            return new ResponseEntity<String>("Khong tim thay id danh muc nay!", HttpStatus.BAD_REQUEST);
        } catch (DeleteInUseDataException e) {
            return new ResponseEntity<ResponseModel<DmKhoanMucModel>>(super.error(4001, "data in use"),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
