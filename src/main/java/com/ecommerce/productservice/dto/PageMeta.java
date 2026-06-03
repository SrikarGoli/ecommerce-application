package com.ecommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageMeta {

    private int page;
    private int size;
    private Long totalElements;
    private int totalPages;
}
