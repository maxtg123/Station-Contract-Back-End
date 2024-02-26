package com.contract.base.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryResultModel<T> {
    private List<T> content = new ArrayList<>();
    private Long totalElements = 0L;
    private Integer totalPages = 0;
    private Integer page = 0;
    private Integer size = 0;

    public Integer getTotalPages() {
        return (int) Math.ceil((double) totalElements / size);
    }
}
