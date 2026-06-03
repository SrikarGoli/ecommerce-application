package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.exception.ObjectNotFoundException;
import com.ecommerce.productservice.mapper.CategoryMapper;
import com.ecommerce.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper mapper;
    private final CategoryRepository repo;

    public CategoryDto findById(Long id){
        return repo.findById(id).map(mapper::toDto)
                .orElseThrow(()-> new ObjectNotFoundException("Category not found with id: "+id));
    }

    public CategoryDto save(CategoryDto categoryDto){
        return mapper.toDto(repo.save(mapper.toEntity(categoryDto)));
    }

}
