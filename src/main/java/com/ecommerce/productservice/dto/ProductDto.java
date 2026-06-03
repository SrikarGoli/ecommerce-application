package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductDto {


    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Positive
    private Double price;
    private Long categoryId;

    private String categoryName;

    private boolean available;
}
