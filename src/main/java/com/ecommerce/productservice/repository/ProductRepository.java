package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor {

//    @Query("Select p from Product p Join Fetch p.category")
    @EntityGraph(attributePaths = "category")
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAll(Specification spec, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByPriceGreaterThan(Double price, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name,Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select p from Product p where p.id= :id")
    Product findByIdWithUpdate(Long id);
}
