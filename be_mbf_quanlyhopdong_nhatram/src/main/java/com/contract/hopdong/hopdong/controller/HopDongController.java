package com.contract.hopdong.hopdong.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.contract.base.controller.BaseController;
import com.contract.base.model.QueryResultModel;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.common.exception.InvalidDataException;
import com.contract.hopdong.hopdong.entity.HopDongEntity;
import com.contract.hopdong.hopdong.enums.LoaiHopDongEnum;
import com.contract.hopdong.hopdong.enums.TinhTrangHopDongEnum;
import com.contract.hopdong.hopdong.enums.TinhTrangThanhToanEnum;
import com.contract.hopdong.hopdong.enums.TrangThaiHoatDongQueryEnum;
import com.contract.hopdong.hopdong.model.HopDongExportDto;
import com.contract.hopdong.hopdong.model.HopDongImportDto;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.hopdong.hopdong.model.LoaiFileInputDto;
import com.contract.hopdong.hopdong.service.ExportBangKeKhaiThanhToanService;
import com.contract.hopdong.hopdong.service.ExportThanhToanChiHoService;
import com.contract.hopdong.hopdong.service.HopDongService;
import com.contract.hopdong.hopdong.service.ImportExportHopDongService;
import com.contract.hopdong.hopdongnhatram.model.BangKeChiHoRequest;
import com.contract.hopdong.hopdongnhatram.model.BangKeKhaiThanhToanTrongKyRequest;
import com.contract.hopdong.hopdongnhatram.model.ThanhToanHopDongRequest;
import com.contract.hopdong.lichsu.service.HopDongLichSuService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@ApiPrefixController
@Tag(name = "Hợp đồng", description = "API quản lý hợp đồng")
public class HopDongController extends BaseController<HopDongModel> {

    private class HopDongResponseClass extends ResponseModel<HopDongModel> {
    }

    @Autowired
    private HopDongService hopDongService;
    @Autowired
    private ImportExportHopDongService importExportHopDongService;
    @Autowired
    private HopDongLichSuService hopDongLichSuService;
    @Autowired
    private ExportThanhToanChiHoService exportThanhToanChiHoService;
    @Autowired
    private ExportBangKeKhaiThanhToanService exportBangKeKhaiThanhToanService;

    @Operation(summary = "Tạo mới hợp đồng", description = "Tạo mới hợp đồng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về hợp đồng mới tạo", content = @Content(schema = @Schema(implementation = HopDongResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleHopDong.kiemTraQuyenThemMoi(#hopDong)")
    @PostMapping(value = "/hop-dong", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<HopDongModel> create(@RequestParam(value = "hopDong") String hopDong,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "infoFiles", required = false) String infoFiles) throws Exception {
        List<LoaiFileInputDto> listFileInfo = null;
        if (infoFiles != null) {
            try {
                ObjectMapper mapperInfoFile = new ObjectMapper();

                listFileInfo = mapperInfoFile.readValue(infoFiles, new TypeReference<List<LoaiFileInputDto>>() {
                });
            } catch (Exception e) {
                System.out.println(e);
                throw new InvalidDataException();
            }
        }
        return super.success(hopDongService.create(hopDong, null, files, listFileInfo));
    }

    @Operation(summary = "Cập nhật hợp đồng", description = "Cập nhật hợp đồng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về hợp đồng mới cập nhật", content = @Content(schema = @Schema(implementation = HopDongResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleHopDong.kiemTraQuyenCapNhat(#hopDong,#id)")
    @PutMapping(value = "/hop-dong/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<HopDongModel> update(@PathVariable("id") Long id,
            @RequestParam(value = "hopDong") String hopDong,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "infoFiles", required = false) String infoFiles,
            @RequestParam(value = "changeLog", required = false) String changeLog) throws Exception {
        List<LoaiFileInputDto> listFileInfo = null;
        if (infoFiles != null) {
            try {
                ObjectMapper mapperInfoFile = new ObjectMapper();

                listFileInfo = mapperInfoFile.readValue(infoFiles, new TypeReference<List<LoaiFileInputDto>>() {
                });
            } catch (Exception e) {
                System.out.println(e);
                throw new InvalidDataException();
            }
        }
        HopDongModel saved = hopDongService.update(id, hopDong, null, files, listFileInfo);

        if (changeLog != null) {
            hopDongLichSuService.create(changeLog, saved.getId());
        }

        return super.success(saved);
    }

    @Operation(summary = "Lấy danh sách hợp đồng", description = "Lấy danh sách hợp đồng")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/hop-dong")
    public ResponseModel<HopDongModel> getAllHopDong(
            /** 0: pagination; 1: all */
            @RequestParam(name = "countOnly", defaultValue = "0") Integer countOnly,
            @RequestParam(name = "responseType", defaultValue = "0") Integer responseType,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            /** SHD, SHD Erp, ma tram, ma tram erp, ma DTXD */
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "maTram", required = false) String maTram,
            @RequestParam(name = "soHopDong", required = false) String soHopDong,
            @RequestParam(name = "soHopDongErp", required = false) String soHopDongErp,
            @RequestParam(name = "ngayKyFrom", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKyFrom,
            @RequestParam(name = "ngayKyTo", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKyTo,
            @RequestParam(name = "ngayKetThucFrom", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKetThucFrom,
            @RequestParam(name = "ngayKetThucTo", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKetThucTo,
            @RequestParam(name = "hinhThucDauTuId", required = false) Integer hinhThucDauTuId,
            @RequestParam(name = "hinhThucKyHopDongId", required = false) Integer hinhThucKyHopDongId,
            @RequestParam(name = "doiTuongKyHopDongId", required = false) Integer doiTuongKyHopDongId,
            @RequestParam(name = "phongDaiId", required = false) Integer phongDaiId,
            @RequestParam(name = "trangThaiHopDong", required = false) TrangThaiHoatDongQueryEnum trangThaiHopDong,
            @RequestParam(name = "loaiHopDong", required = false) LoaiHopDongEnum loaiHopDong,
            @RequestParam(name = "idTinh", required = false) Integer idTinh,
            @RequestParam(name = "idHuyen", required = false) Integer idHuyen,
            @RequestParam(name = "idXa", required = false) Integer idXa,
            @RequestParam(name = "tinhTrangHopDong", required = false) TinhTrangHopDongEnum tinhTrangHopDong,
            @RequestParam(name = "tinhTrangThanhToan", required = false) TinhTrangThanhToanEnum tinhTrangThanhToan,
            @RequestParam(name = "kyThanhToanFrom", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date kyThanhToanFrom,
            @RequestParam(name = "kyThanhToanTo", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date kyThanhToanTo)
            throws Exception {
        QueryResultModel<HopDongEntity> hopDongEntityPage = hopDongService.findAll(countOnly, page, size,
                responseType, search, soHopDong, soHopDongErp, hinhThucDauTuId, hinhThucKyHopDongId,
                doiTuongKyHopDongId, phongDaiId, trangThaiHopDong, loaiHopDong, maTram, ngayKyFrom, ngayKyTo,
                ngayKetThucFrom, ngayKetThucTo, idTinh, idHuyen, idXa, tinhTrangHopDong, tinhTrangThanhToan,
                kyThanhToanFrom, kyThanhToanTo);

        List<HopDongModel> hopDongModels = hopDongEntityPage.getContent().stream()
                .map(entity -> HopDongModel.fromEntity(entity, true)).collect(Collectors.toList());
        return super.success(hopDongModels, hopDongEntityPage.getTotalPages(), page, size,
                hopDongEntityPage.getTotalElements());
    }

    @Operation(summary = "Lấy chi tiết hợp đồng", description = "Lấy chi tiết hợp đồng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về chi tiết hợp đồng", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HopDongResponseClass.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema())), })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/hop-dong/{id}")
    public ResponseModel<HopDongModel> getDetailHopDong(@PathVariable("id") Long id) throws Exception {
        HopDongModel hopDongModel = hopDongService.getDetail(id);
        return super.success(hopDongModel);
    }

    @Operation(summary = "Import hợp đồng", description = "Import hợp đồng")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/hop-dong/import", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long importHopDong(@RequestBody List<HopDongImportDto> request) throws Exception {
        return hopDongService.importHopDong(request);
    }

    @Operation(summary = "Export hợp đồng", description = "Export hợp đồng")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/hop-dong/export")
    public ResponseEntity<?> exportHopDong(
            @RequestBody HopDongExportDto hopDongExportDto,
            /** From filter of api list */
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "maTram", required = false) String maTram,
            @RequestParam(name = "soHopDong", required = false) String soHopDong,
            @RequestParam(name = "soHopDongErp", required = false) String soHopDongErp,
            @RequestParam(name = "ngayKyFrom", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKyFrom,
            @RequestParam(name = "ngayKyTo", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKyTo,
            @RequestParam(name = "ngayKetThucFrom", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKetThucFrom,
            @RequestParam(name = "ngayKetThucTo", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKetThucTo,
            @RequestParam(name = "hinhThucDauTuId", required = false) Integer hinhThucDauTuId,
            @RequestParam(name = "hinhThucKyHopDongId", required = false) Integer hinhThucKyHopDongId,
            @RequestParam(name = "doiTuongKyHopDongId", required = false) Integer doiTuongKyHopDongId,
            @RequestParam(name = "phongDaiId", required = false) Integer phongDaiId,
            @RequestParam(name = "trangThaiHopDong", required = false) TrangThaiHoatDongQueryEnum trangThaiHopDong,
            @RequestParam(name = "loaiHopDong", required = false) LoaiHopDongEnum loaiHopDong,
            @RequestParam(name = "idTinh", required = false) Integer idTinh,
            @RequestParam(name = "idHuyen", required = false) Integer idHuyen,
            @RequestParam(name = "idXa", required = false) Integer idXa,
            @RequestParam(name = "tinhTrangHopDong", required = false) TinhTrangHopDongEnum tinhTrangHopDong,
            @RequestParam(name = "tinhTrangThanhToan", required = false) TinhTrangThanhToanEnum tinhTrangThanhToan,
            @RequestParam(name = "kyThanhToanFrom", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date kyThanhToanFrom,
            @RequestParam(name = "kyThanhToanTo", required = false) @Parameter(description = "yyyy-mm-yy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date kyThanhToanTo)
            throws Exception {
        return importExportHopDongService.export(hopDongExportDto.getListKey(), hopDongExportDto.getListId(),
                hopDongExportDto.getExcludeKey(), search,
                soHopDong, soHopDongErp, hinhThucDauTuId,
                hinhThucKyHopDongId,
                doiTuongKyHopDongId, phongDaiId, trangThaiHopDong, loaiHopDong, maTram, ngayKyFrom, ngayKyTo,
                ngayKetThucFrom, ngayKetThucTo, idTinh, idHuyen, idXa, tinhTrangHopDong, tinhTrangThanhToan,
                kyThanhToanFrom, kyThanhToanTo);
    }

    @Operation(summary = "Xóa hợp đồng có trạng thái NHAP", description = "\"Xóa hợp đồng có trạng thái NHAP")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/hop-dong/delete-type-nhap/{id}")
    public ResponseEntity<?> deleteHopDongTypeNhap(@PathVariable("id") Long id) throws Exception {
        return hopDongService.deleteHopDongTypeNhapByIdHopDong(id);
    }

    @Operation(summary = "Xuất template đề nghị chi hộ", description = "Xuất template đề nghị chi hộ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xuất template đề nghị chi hộ", content = @Content(array = @ArraySchema(schema = @Schema()))),
            @ApiResponse(responseCode = "400", description = "Chưa chọn hợp đồng cần xuất", content = @Content(array = @ArraySchema(schema = @Schema()))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema())), })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/hop-dong/export/de-nghi-chi-ho", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> exportTemplateDeNghiChiHo(@RequestBody BangKeChiHoRequest request) throws Exception {
        return exportThanhToanChiHoService.exportTemplateDeNghiChiHo(request);
    }

    @Operation(summary = "Xuất template Bảng kê khai thanh toán trong kỳ", description = "Xuất template Bảng kê khai thanh toán trong kỳ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xuất template Bảng kê khai thanh toán trong kỳ", content = @Content(array = @ArraySchema(schema = @Schema()))),
            @ApiResponse(responseCode = "400", description = "Chưa chọn hợp đồng cần xuất", content = @Content(array = @ArraySchema(schema = @Schema()))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema())), })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/hop-dong/export/bang-ke-khai-thanh-toan")
    public ResponseEntity<?> exportTemplateBangKeKhaiThanhToanTrongKy(
            @RequestBody BangKeKhaiThanhToanTrongKyRequest request) throws Exception {
        return exportBangKeKhaiThanhToanService.exportTemplateBangKeKhaiThanhToanTrongKy(request);
    }

    @Operation(summary = "Thanh toán hợp đồng", description = "Thanh toán hợp đồng")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách hợp đồng vừa thực hiện thanh toán", content = @Content(schema = @Schema(implementation = HopDongResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleHopDong.kiemTraQuyenCapNhat(#hopDong,#id)")
    @PutMapping(value = "/hop-dong/thanh-toan")
    public ResponseEntity<?> thanhToanHopDong(
            @RequestBody List<ThanhToanHopDongRequest> listIdHopDong) {
        hopDongService.thanhToanHopDong(listIdHopDong);
        return new ResponseEntity<String>("Xoa hoan tat!", HttpStatus.OK);
    }
}
