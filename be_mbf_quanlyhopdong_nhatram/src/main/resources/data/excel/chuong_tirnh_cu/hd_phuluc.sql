-- GET PHU LUC TU CHUONG TRINH CU
select 
--distinct count(phu_luc.phu_luc_id)
phu_luc.phu_luc_id as id, hop_dong.so_hop_dong, phu_luc.ngay_ky, phu_luc.ngay_hieu_luc, phu_luc.ngay_ket_thuc, phu_luc.so_phu_luc, phu_luc.isactive, phu_luc.ghi_chu
from hop_dong
right join phu_luc on hop_dong.hop_dong_id = phu_luc.hop_dong_id
where hop_dong.so_hop_dong is not null
order by hop_dong.ngay_ket_thuc_hd desc, hop_dong.so_hop_dong;

-- Get files from chuong trinh cu
select 
a.so_hop_dong, a.file_list ,b.file_id, b.file_path
from hop_dong a
left join files b on instr(';' || a.file_list || ';', ';' || b.file_id || ';') > 0 
where  a.file_list is not null and b.file_id is not null and b.file_path is not null and a.so_hop_dong is not null;

-- Get thu huong tu  chuong trinh cu
select  trim(a.so_hop_dong) as so_hop_dong, trim(b.ho_ten) as ho_ten, trim(b.so_tai_khoan) as so_tai_khoan, 
trim(c.ten_ngan_hang) as ten_ngan_hang, e.don_vi_quan_ly, f.ten_phong_ban,
trim(b.chi_nhanh) chi_nhanh, a.TRANG_THAI_HD_PL_ID
from hop_dong a
left join doi_tuong_hd b on a.nguoi_thu_huong = b.doi_tuong_hd_id 
left join dm_ngan_hang c on b.ngan_hang_id = c.ngan_hang_id
left join hop_dong_hang_muc_mb d on a.hop_dong_id = d.hop_dong_id
left join site e on d.site_id = e.site_id
left join dm_phong_ban f on f.phong_ban_id = e.don_vi_quan_ly
where a.so_hop_dong is not null and a.loai_hd = 1 and e.ma_site is not null and e.don_vi_quan_ly is not null
order by a.so_hop_dong;


REM INSERTING into EXPORT_TABLE
SET DEFINE OFF;
Insert into EXPORT_TABLE (SO_HOP_DONG,NGAY_KY,NGAY_HIEU_LUC,NGAY_KET_THUC,SO_PHU_LUC,ISACTIVE,GHI_CHU) values ('2776/HĐMB',to_date('27-08-2007','DD-MM-RRRR'),to_date('01-07-2017','DD-MM-RRRR'),to_date('27-08-2057','DD-MM-RRRR'),'PL05','2',null);
