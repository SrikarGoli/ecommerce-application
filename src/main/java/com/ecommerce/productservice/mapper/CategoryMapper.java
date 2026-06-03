package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category){
        CategoryDto dto=new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
    public Category toEntity(CategoryDto categoryDto){
        Category category=new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }
}
