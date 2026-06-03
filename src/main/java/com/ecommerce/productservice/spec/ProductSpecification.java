package com.ecommerce.productservice.spec;

import com.ecommerce.productservice.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return null;
            return cb.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Product> hasMinPrice(Double price) {
        return (root, query, cb) -> {
            if (price == null) return null;
            return cb.greaterThanOrEqualTo(root.get("price"), price);
        };
    }

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(
                    cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }
}
