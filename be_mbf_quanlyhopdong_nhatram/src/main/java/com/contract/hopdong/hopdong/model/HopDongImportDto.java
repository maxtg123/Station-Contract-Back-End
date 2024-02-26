package com.contract.hopdong.hopdong.model;

import java.util.Date;
import java.util.List;

import com.contract.hopdong.hopdong.enums.LoaiHopDongEnum;
import com.contract.hopdong.hopdong.enums.TinhTrangHopDongEnum;
import com.contract.hopdong.hopdong.enums.TrangThaiHopDongEnum;
import com.contract.hopdong.hopdongdoitac.model.HopDongDoiTacModel;
import com.contract.hopdong.hopdongtram_dungchung.model.HopDongTramDungChungModel;
import com.contract.hopdong.hopdongtram_kythanhtoan.model.HopDongTramKyThanhToanModel;
import com.contract.hopdong.hopdongtram_phutro.model.HopDongTramPhuTroModel;
import com.contract.tram.tram.model.TramModel;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class HopDongImportDto {

    public static HopDongModel convertToHopDongModelFromDto(HopDongImportDto dto) {
        HopDongModel model = new HopDongModel();
        model.setSoHopDong(dto.getSoHopDong());
        model.setSoHopDongErp(dto.getSoHopDongErp());
        model.setHinhThucKyId(dto.getHinhThucKyId());
        model.setHinhThucDauTuId(dto.getHinhThucDauTuId());
        model.setDoiTuongKyId(dto.getDoiTuongKyId());
        model.setHinhThucThanhToanId(dto.getHinhThucThanhToanId());
        model.setCoKyQuy(dto.getCoKyQuy());
        model.setNgayKy(dto.getNgayKy());
        model.setNgayKetThuc(dto.getNgayKetThuc());
        model.setGhiChu(dto.getGhiChu());
        model.setThueVat(dto.getThueVat());
        model.setTrangThaiHopDong(TrangThaiHopDongEnum.NHAP);
        model.setTinhTrangHopDong(dto.getTinhTrangHopDong());
        model.setLoaiHopDong(dto.getLoaiHopDong());
        model.setChuKyNam(dto.getChuKyNam());
        model.setChuKyThang(dto.getChuKyThang());
        model.setChuKyNgay(dto.getChuKyNgay());
        model.setGiaKyQuy(dto.getGiaKyQuy());
        model.setKhoanMucId(dto.getKhoanMucId());
        model.setHopDongDoiTac(dto.getHopDongDoiTac());
        return model;
    }

    protected Long tramId;
    protected String soHopDong;
    protected String soHopDongErp;
    protected Integer hinhThucKyId;
    protected Integer hinhThucDauTuId;
    protected Integer doiTuongKyId;
    protected Boolean coKyQuy;
    protected Date ngayKy;
    protected Date ngayKetThuc;
    protected String ghiChu;
    protected Double giaThue;
    protected Integer thueVat;
    protected Integer hinhThucThanhToanId;
    private Integer chuKyNam;
    private Integer chuKyThang;
    private Integer chuKyNgay;
    private Date ngayBatDauYeuCauThanhToan;
    private Double giaDienKhoan;
    private Double giaKyQuy;
    private Integer khoanMucId;
    private LoaiHopDongEnum loaiHopDong;
    private TinhTrangHopDongEnum tinhTrangHopDong;
    // private Long loaiPhongMayId;
    // private Long loaiPhongMayPhatDienId;
    // private Long loaiTramVhktId;
    private TramModel tram;
    private HopDongDoiTacModel hopDongDoiTac;
    private HopDongTramDungChungModel hopDongDungChung;
    private List<HopDongTramPhuTroModel> hopDongPhuTroList;

    private List<HopDongTramKyThanhToanModel> hopDongKyThanhToanList;
}
