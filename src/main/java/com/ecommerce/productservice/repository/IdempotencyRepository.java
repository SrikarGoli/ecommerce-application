package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.IdempotencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyRepository extends JpaRepository<IdempotencyRecord,String> {
}
