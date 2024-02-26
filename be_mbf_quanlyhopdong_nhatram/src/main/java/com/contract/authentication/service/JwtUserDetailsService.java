package com.contract.authentication.service;

import java.util.ArrayList;
import java.util.List;

import com.contract.nguoidung.nguoidung.model.TrangThai;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.contract.nguoidung.nguoidung.entity.NguoiDungEntity;

@Service
@Lazy
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private NguoiDungService nguoiDungService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<NguoiDungEntity> listNguoiDung = nguoiDungService.findByEmail(username);
        if (ObjectUtils.isEmpty(listNguoiDung)) {
            throw new UsernameNotFoundException("Nguoi dung not found with username: " + username);
        }
        NguoiDungEntity nguoiDung = listNguoiDung.get(0);
        if (TrangThai.NGUNG_HOAT_DONG.equals(nguoiDung.getTrangThai())) {
            throw new UsernameNotFoundException("Nguoi dung not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(nguoiDung.getEmail(),
                nguoiDung.getMatKhau(), new ArrayList<>());
    }
}
