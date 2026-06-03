package com.ecommerce.productservice.dto;

import lombok.Data;

@Data
public class OrderItemResponse {

    private Long productId;
    private Integer quantity;
    private Double price;
}
