package com.ecommerce.productservice.config;

import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.CategoryRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.boot.CommandLineRunner;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    @Override
    public void run(String... args) {

        // Prevent duplicate inserts on restart
        if (categoryRepository.count() > 0) {
            return;
        }

        Category electronics = categoryRepository.save(
                Category.builder()
                        .name("Electronics")
                        .build()
        );

        Category fashion = categoryRepository.save(
                Category.builder()
                        .name("Fashion")
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("iPhone")
                        .price(100000.0)
                        .stock(5)
                        .category(electronics)
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("Laptop")
                        .price(80000.0)
                        .stock(5)
                        .category(electronics)
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("T-Shirt")
                        .price(1000.0)
                        .stock(5)
                        .category(fashion)
                        .build()
        );

        productRepository.save(
                Product.builder().name("TV").price(60000.0).stock(5).category(electronics).build()
        );

        productRepository.save(
                Product.builder().name("Headphones").price(5000.0).stock(5).category(electronics).build()
        );

        productRepository.save(
                Product.builder().name("Jeans").price(2000.0).stock(5).category(fashion).build()
        );

        productRepository.save(
                Product.builder().name("Shoes").price(3000.0).stock(5).category(fashion).build()
        );
        System.out.println("data loaded successfully");
    }
}
