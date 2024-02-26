package com.contract.tram.tram.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contract.base.controller.BaseController;
import com.contract.base.model.ResponseModel;
import com.contract.common.annotation.ApiPrefixController;
import com.contract.hopdong.hopdong.model.HopDongModel;
import com.contract.hopdong.hopdong.repository.HopDongRepository;
import com.contract.tram.tram.entity.TramEntity;
import com.contract.tram.tram.model.TramModel;
import com.contract.tram.tram.model.TrangThaiTram;
import com.contract.tram.tram.service.SyncTramService;
import com.contract.tram.tram.service.TramService;

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
@Tag(name = "Trạm", description = "API quản lý trạm")
public class TramController extends BaseController<TramModel> {

    private class TramResponseClass extends ResponseModel<TramModel> {
    }

    @Autowired
    private HopDongRepository hopDongRepository;

    @Autowired
    private TramService tramService;

    @Autowired
    private SyncTramService syncTramService;

    @Operation(summary = "Xem trạm", description = "Xem trạm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xem trạm", content = @Content(schema = @Schema(implementation = TramResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/tram/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel<TramModel> findById(@PathVariable Long id) throws Exception {
        TramModel tramModel = tramService.findChiTietTram(id);
        return super.success(tramModel);
    }

    @Operation(summary = "Lấy danh sách trạm", description = "Lấy danh sách trạm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách trạm", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TramResponseClass.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema())), })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/tram", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel<TramModel> getAll(
            @RequestParam(name = "responseType", defaultValue = "1") Integer responseType, // 1='pageable'
                                                                                           // ||
                                                                                           // 0='get
                                                                                           // all'
            @RequestParam(name = "size", defaultValue = "10") Integer limit,
            @RequestParam(name = "page", defaultValue = "0") Integer offset,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "tinh", required = false) Integer tinh,
            @RequestParam(name = "huyen", required = false) Integer huyen,
            @RequestParam(name = "xa", required = false) Integer xa,
            @RequestParam(name = "phongDai", required = false) Integer phongDai,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "trangThaiHoatDong", required = false) TrangThaiTram trangThaiHoatDong,
            @RequestParam(name = "loaiCsht", required = false) Integer loaiCsht,
            @RequestParam(name = "trangThaiPhatSong", required = false) Boolean trangThaiPhatSong)
            throws Exception {
        Page<TramEntity> tramEntityPage = this.tramService.findAll(responseType, search, tinh, huyen, xa, phongDai, to,
                trangThaiHoatDong, loaiCsht, trangThaiPhatSong, limit, offset);

        List<TramModel> tramModelList = new ArrayList<>();
        if (responseType.equals(Integer.valueOf(1))) {
            tramModelList = this.tramService.convertListTramEntityToModel(tramEntityPage);
        } else {
            for (int i = 0; i < tramEntityPage.getContent().size(); i++) {
                TramEntity tramEntity = tramEntityPage.getContent().get(i);
                tramModelList.add(TramModel.fromEntity(tramEntity, false));
            }
        }

        return super.success(tramModelList, tramEntityPage.getTotalPages(), offset, limit,
                tramEntityPage.getTotalElements());
    }

    @Operation(summary = "Tạo mới trạm", description = "Tạo mới trạm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về trạm mới tạo", content = @Content(schema = @Schema(implementation = TramResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleTram.kiemTraQuyenThemMoi(#tram)")
    @PostMapping(value = "/tram", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel<TramModel> create(@RequestBody TramModel tram) throws Exception {
        return super.success(tramService.create(tram));
    }

    @Operation(summary = "Cập nhật trạm", description = "Cập nhật trạm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về trạm mới cập nhật", content = @Content(schema = @Schema(implementation = TramResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleTram.kiemTraQuyenCapNhat(#tram,#id)")
    @PutMapping(value = "/tram/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel<TramModel> update(@RequestBody TramModel tram, @PathVariable Long id)
            throws Exception {
        return super.success(tramService.update(tram, id));
    }

    @Operation(summary = "Xóa trạm", description = "Xóa trạm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về trạm vừa xóa", content = @Content(schema = @Schema(implementation = TramResponseClass.class))) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("@kiemTraQuyenModuleTram.kiemTraQuyenXoa(#id)")
    @DeleteMapping(value = "/tram/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel<TramModel> delete(@PathVariable Long id) throws Exception {
        return super.success(tramService.delete(id));
    }

    @Operation(summary = "Kiểm tra trạm tồn tại", description = "Kiểm tra trạm tồn tại")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về kết quả kiểm tra trạm có tồn tại hay không", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Boolean.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema())), })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/tram/checkExists", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean checkExists(@RequestParam("maTram") String maTram,
            @RequestParam(value = "maTramErp", required = false) String maTramErp)
            throws Exception {
        return this.tramService.checkExists(null, maTram, maTramErp);
    }

    @Operation(summary = "Xuất danh sách trạm ra file excel theo các trường được chọn", description = "Chọn true nếu muốn thêm trường này vào danh sách các trường sẽ export")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về file excel đã export theo các trường được chọn", content = @Content(array = @ArraySchema(schema = @Schema()))),
            @ApiResponse(responseCode = "400", description = "Chưa chọn trường dữ liệu xuất", content = @Content(array = @ArraySchema(schema = @Schema()))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema())), })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/tram/export")
    public ResponseEntity<?> exportToExcel(@RequestBody List<String> listIdToExport,
            @RequestParam(value = "export_all_column", required = false) boolean exportAllColumn,
            @RequestParam(value = "maDTXD", required = false) boolean maDTXD,
            @RequestParam(value = "dmPhongDai", required = false) boolean phongDaiId,
            @RequestParam(value = "dmTo", required = false) boolean toId,
            @RequestParam(value = "maTram", required = false) boolean maTram,
            @RequestParam(value = "maTramErp", required = false) boolean maTramErp,
            @RequestParam(value = "ten", required = false) boolean ten,
            @RequestParam(value = "dmTinh", required = false) boolean tinhId,
            @RequestParam(value = "dmHuyen", required = false) boolean huyenId,
            @RequestParam(value = "dmXa", required = false) boolean xaId,
            @RequestParam(value = "diaChi", required = false) boolean diaChi,
            @RequestParam(value = "kinhDo", required = false) boolean kinhDo,
            @RequestParam(value = "viDo", required = false) boolean viDo,
            @RequestParam(value = "dmTramKhuVuc", required = false) boolean khuVucId,
            @RequestParam(value = "ngayPhatSong", required = false) boolean ngayPhatSong,
            @RequestParam(value = "dmLoaiCsht", required = false) boolean loaiCshtId,
            @RequestParam(value = "dmLoaiTram", required = false) boolean loaiTramId,
            @RequestParam(value = "dmLoaiCotAngten", required = false) boolean loaiCotAngtenId,
            @RequestParam(value = "doCaoAngten", required = false) boolean doCaoAngten,
            @RequestParam(value = "ghiChu", required = false) boolean ghiChu,
            @RequestParam(value = "trangThaiHoatDong", required = false) boolean trangThaiHoatDong) {
        LinkedHashMap<String, String> listCacTruongCanExport = new LinkedHashMap<>();
        if (exportAllColumn) {
            listCacTruongCanExport.put("maTram", "Mã Trạm");
            listCacTruongCanExport.put("maDauTuXayDung", "Mã đầu tư xây dựng");
            listCacTruongCanExport.put("dmPhongDaiEntity", "Phòng Đài");
            listCacTruongCanExport.put("dmToEntity", "Tổ");
            listCacTruongCanExport.put("maTramErp", "Mã Trạm ERP");
            listCacTruongCanExport.put("ten", "Tên Trạm");
            listCacTruongCanExport.put("dmTinhEntity", "Tỉnh");
            listCacTruongCanExport.put("dmHuyenEntity", "Huyện");
            listCacTruongCanExport.put("dmXaEntity", "Xã");
            listCacTruongCanExport.put("diaChi", "Địa Chỉ");
            listCacTruongCanExport.put("kinhDo", "Kinh Độ");
            listCacTruongCanExport.put("viDo", "Vĩ Độ");
            listCacTruongCanExport.put("dmTramKhuVucEntity", "Khu Vực");
            listCacTruongCanExport.put("ngayPhatSong", "Ngày Phát Sóng");
            listCacTruongCanExport.put("dmLoaiCshtEntity", "Loại Cơ Sở Hạ Tầng");
            listCacTruongCanExport.put("dmLoaiTramEntity", "Loại Trạm");
            listCacTruongCanExport.put("dmLoaiCotAngtenEntity", "Loại Cột Angten");
            listCacTruongCanExport.put("doCaoAngten", "Độ Cao Angten");
            listCacTruongCanExport.put("ghiChu", "Ghi Chú");
            listCacTruongCanExport.put("trangThaiHoatDong", "Trạng Thái Hoạt Động");
        }
        if (maTram) {
            listCacTruongCanExport.put("maTram", "Mã Trạm");
        }
        if (maDTXD) {
            listCacTruongCanExport.put("maDauTuXayDung", "Mã đầu tư xây dựng");
        }
        if (phongDaiId) {
            listCacTruongCanExport.put("dmPhongDaiEntity", "Phòng Đài");
        }
        if (toId) {
            listCacTruongCanExport.put("dmToEntity", "Tổ");
        }

        if (maTramErp) {
            listCacTruongCanExport.put("maTramErp", "Mã Trạm ERP");
        }
        if (ten) {
            listCacTruongCanExport.put("ten", "Tên Trạm");
        }
        if (tinhId) {
            listCacTruongCanExport.put("dmTinhEntity", "Tỉnh");
        }
        if (huyenId) {
            listCacTruongCanExport.put("dmHuyenEntity", "Huyện");
        }
        if (xaId) {
            listCacTruongCanExport.put("dmXaEntity", "Xã");
        }
        if (diaChi) {
            listCacTruongCanExport.put("diaChi", "Địa Chỉ");
        }
        if (kinhDo) {
            listCacTruongCanExport.put("kinhDo", "Kinh Độ");
        }
        if (viDo) {
            listCacTruongCanExport.put("viDo", "Vĩ Độ");
        }
        if (khuVucId) {
            listCacTruongCanExport.put("dmTramKhuVucEntity", "Khu Vực");
        }
        if (ngayPhatSong) {
            listCacTruongCanExport.put("ngayPhatSong", "Ngày Phát Sóng");
        }
        if (loaiCshtId) {
            listCacTruongCanExport.put("dmLoaiCshtEntity", "Loại Cơ Sở Hạ Tầng");
        }
        if (loaiTramId) {
            listCacTruongCanExport.put("dmLoaiTramEntity", "Loại Trạm");
        }
        if (loaiCotAngtenId) {
            listCacTruongCanExport.put("dmLoaiCotAngtenEntity", "Loại Cột Angten");
        }
        if (doCaoAngten) {
            listCacTruongCanExport.put("doCaoAngten", "Độ Cao Angten");
        }
        if (ghiChu) {
            listCacTruongCanExport.put("ghiChu", "Ghi Chú");
        }
        if (trangThaiHoatDong) {
            listCacTruongCanExport.put("trangThaiHoatDong", "Trạng Thái Hoạt Động");
        }
        return tramService.exportToExcel(listCacTruongCanExport, listIdToExport);
    }

    @Operation(summary = "Import trạm", description = "Import trạm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Import thành công", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TramModel.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema())), })
    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping(value = "/tram/import/{loaiImport}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importTram(@RequestBody List<TramModel> listTramToImport,
            @Parameter(description = "Thêm mới: loaiImport = create <br> Cập nhật: loaiImport = update") @PathVariable("loaiImport") String loaiImport) {
        return tramService.importFromExcel(listTramToImport, loaiImport);
    }

    @Operation(summary = "Thống kê trạm", description = "Thống kê trạm")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/tram/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> thongKeTram() {
        return ResponseEntity.of(Optional.of(tramService.thongKeTram()));
    }

    @Operation(summary = "Đồng bộ trạm từ PTM", description = "Đồng bộ trạm từ PTM")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/tram/syncFromPTM", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long syncTramFromPTM() throws Exception {
        return syncTramService.syncFromPtm();
    }

    @Operation(summary = "Danh sách Hợp đồng theo id trạm", description = "Danh sách Hợp đồng theo id trạm")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/tram/hop-dong-by-tram-id")
    public ResponseEntity<List<HopDongModel>> getAllHopDongByTramId(
            @RequestParam(name = "tramId", required = false) Long tramId)
            throws Exception {
        List<HopDongModel> list = hopDongRepository.findHopDongByIdTram(tramId).stream()
                .map(_entity -> HopDongModel.fromEntity(_entity, true)).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
