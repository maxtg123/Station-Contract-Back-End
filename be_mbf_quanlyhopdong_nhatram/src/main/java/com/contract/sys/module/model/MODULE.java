package com.contract.sys.module.model;

import java.util.ArrayList;
import java.util.List;

public enum MODULE {
    NGUOI_DUNG("Người dùng"),
    CHUC_VU("Chức vụ"),
    DANH_MUC("Danh mục"),
    TRAM("Trạm"),
    HOP_DONG("Hợp đồng"),
    BAO_CAO("Báo cáo"),
    DAM_PHAN("Đàm phán");

    private final String label;

    MODULE(String label) {
        this.label = label;
    }

    public static MODULE valueOfLabel(String label) {
        for (MODULE e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }

    public static List<String> getAllValue() {
        List<String> listValue = new ArrayList<>();
        for (MODULE e : values()) {
            listValue.add(e.label);
        }
        return listValue;
    }

}