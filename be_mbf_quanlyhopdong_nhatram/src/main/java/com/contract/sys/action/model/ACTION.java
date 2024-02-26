package com.contract.sys.action.model;

import java.util.ArrayList;
import java.util.List;

public enum ACTION {
    XEM("Xem"),
    THEM_MOI("Thêm mới"),
    CAP_NHAT("Cập nhật"),
    IMPORT("Import"),
    XET_DUYET("Xét duyệt"),
    GIAO_VIEC("Giao việc"),
    NHAN_VIEC("Nhận việc");

    private final String label;

    ACTION(String label) {
        this.label = label;
    }

    public static ACTION valueOfLabel(String label) {
        for (ACTION e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }

    public static List<String> getAllValue() {
        List<String> listValue = new ArrayList<>();
        for (ACTION e : values()) {
            listValue.add(e.label);
        }
        return listValue;
    }
}