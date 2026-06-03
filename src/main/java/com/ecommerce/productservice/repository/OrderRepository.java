package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
