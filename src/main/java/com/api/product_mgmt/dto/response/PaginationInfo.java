package com.api.product_mgmt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationInfo {
    private Integer totalElements;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPages;

    public PaginationInfo(Integer page, Integer size, Integer totalElements) {
        this.totalElements = totalElements;
        this.currentPage = page;
        this.pageSize = size;
        this.totalPages = (totalElements / size) + (totalElements % size > 0 ? 1 : 0);
    }
}
