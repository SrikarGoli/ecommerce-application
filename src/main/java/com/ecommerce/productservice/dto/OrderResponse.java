package com.ecommerce.productservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Double totalAmount;
    private List<OrderItemResponse> items;
}
