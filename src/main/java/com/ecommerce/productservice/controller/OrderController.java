package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.CreateOrderRequest;
import com.ecommerce.productservice.dto.OrderResponse;
import com.ecommerce.productservice.entity.IdempotencyRecord;
import com.ecommerce.productservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("Idempotency-Key") String key, @RequestBody @Valid CreateOrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(key, request));
    }
}
