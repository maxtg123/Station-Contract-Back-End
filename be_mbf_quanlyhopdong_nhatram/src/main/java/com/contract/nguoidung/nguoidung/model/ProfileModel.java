package com.contract.nguoidung.nguoidung.model;

import com.contract.nguoidung.nguoidungkhuvuc.model.NguoiDungKhuVucModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileModel {
    private long id;
    private String email;
    private String hoTen;
    private String gioiTinh;
    private String soDienThoai;
    private String trangThai;
    private List<NguoiDungKhuVucModel> nguoiDungKhuVucList;
}
