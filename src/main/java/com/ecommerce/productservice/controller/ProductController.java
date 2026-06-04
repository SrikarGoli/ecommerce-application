package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ApiResponse;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.dto.StockUpdateRequest;
import com.ecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;



    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAll(Pageable pageable){
        return ResponseEntity.ok(productService.findAll(pageable));
    }
//    @GetMapping("/search")
//    public ResponseEntity<Page<ProductDto>> search(
//            @RequestParam(required = false) Long categoryId,
//            @RequestParam(required = false) Double minPrice,
//            @RequestParam(required = false) String name,
//            Pageable pageable){
//        if(categoryId!=null){
//            return ResponseEntity.ok(productService.findByCategoryId(categoryId,pageable));
//        }
//        if(minPrice!=null){
//            return ResponseEntity.ok(productService.findByPrice(minPrice,pageable));
//        }
//        if(name!=null){
//            return ResponseEntity.ok(productService.findByName(name,pageable));
//        }
//        return ResponseEntity.ok(productService.findAll(pageable));
//    }


    @GetMapping("/multiquerysearch")
    public ResponseEntity<ApiResponse<List<ProductDto>>> multiQuerySearch(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) String name,
            Pageable pageable) {

        return ResponseEntity.ok(
                productService.search(categoryId, minPrice, name, pageable)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }


    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(
            @PathVariable Long id,
            @RequestBody @Valid StockUpdateRequest request) {

        productService.updateStock(id, request.getStock());
        return ResponseEntity.ok().build();
    }
}
