package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ApiResponse;
import com.ecommerce.productservice.dto.PageMeta;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ObjectNotFoundException;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.spec.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;


    public ProductDto save(ProductDto dto){
        return mapper.toDto(repo.save(mapper.toEntity(dto)));
    }
    
    public ApiResponse<List<ProductDto>> findAll(Pageable pageable){
        Page<Product> page =repo.findAll(pageable);
        List<ProductDto> list=page.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();

        PageMeta object=new PageMeta(page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages());

        return new ApiResponse<>(list,object);
    }

    public ProductDto findById(Long id){
        log.info("Fetching product with id: {}", id);
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(()->new ObjectNotFoundException("Product not found with id: "+id));
    }


    @Transactional
    public void updateStock(Long id, Integer stock) {

        log.info("Updating stock for product: {}, new stock: {}", id, stock);
        Product product = repo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Product not found with id: " + id));

        product.setStock(stock);
        log.info("Stock updated successfully for product: {}", id);
    }

//    public Page<ProductDto> findByCategoryId(Long categoryId, Pageable pageable){
//        return repo.findByCategoryId(categoryId,pageable).map(mapper::toDto);
//    }
//
//    public Page<ProductDto> findByPrice(Double price, Pageable pageable){
//        return repo.findByPriceGreaterThan(price,pageable).map(mapper::toDto);
//    }
//
//    public Page<ProductDto> findByName(String name, Pageable pageable){
//        return repo.findByNameContainingIgnoreCase(name,pageable).map(mapper::toDto);
//    }


    public ApiResponse<List<ProductDto>> search(
            Long categoryId,
            Double minPrice,
            String name,
            Pageable pageable) {

        Specification<Product> spec = Specification
                .where(ProductSpecification.hasCategory(categoryId))
                .and(ProductSpecification.hasMinPrice(minPrice))
                .and(ProductSpecification.hasName(name));

        Page<Product> page =repo.findAll(spec,pageable);
        List<ProductDto> list=page.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();

        PageMeta object=new PageMeta(page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages());

        return new ApiResponse<>(list,object);
    }

}
