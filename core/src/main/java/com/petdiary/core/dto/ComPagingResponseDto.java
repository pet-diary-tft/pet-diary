package com.petdiary.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComPagingResponseDto {
    private Integer totalPage;
    private Long totalCount;
    private Integer currentPage;
    private Integer size;

    public ComPagingResponseDto(Integer totalPage, Long totalCount, Integer currentPage, Integer size) {
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.currentPage = currentPage + 1;
        this.size = size;
    }
}
