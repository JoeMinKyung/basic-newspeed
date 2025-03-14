package com.example.springbasicnewspeed.domain.post.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {

    private List<T> data;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;

    public PageResponse(List<T> data, int pageNumber, int pageSize, int totalPages, long totalElements) {
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
