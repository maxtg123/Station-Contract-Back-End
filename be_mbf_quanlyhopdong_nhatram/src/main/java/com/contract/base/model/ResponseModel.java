package com.contract.base.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponseModel<T> {
    @Data
    public static class Status {
        private boolean success;
        private String errors;
        private int code;
    }
    @Data
    public static class Metadata {
        private int page;
        private int perPage;
        private int pageCount;
        private long total;
    }

    private Status status;
    private Metadata metadata;
    private List<T> elements;
}
