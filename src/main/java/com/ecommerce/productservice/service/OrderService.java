package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.CreateOrderRequest;
import com.ecommerce.productservice.dto.OrderItemRequest;
import com.ecommerce.productservice.dto.OrderResponse;
import com.ecommerce.productservice.entity.IdempotencyRecord;
import com.ecommerce.productservice.entity.Order;
import com.ecommerce.productservice.entity.OrderItem;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.InsufficientStockException;
import com.ecommerce.productservice.exception.ObjectNotFoundException;
import com.ecommerce.productservice.mapper.OrderMapper;
import com.ecommerce.productservice.repository.IdempotencyRepository;
import com.ecommerce.productservice.repository.OrderRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final IdempotencyRepository idempotencyRepository;

    @Transactional
    public OrderResponse placeOrder(String key, CreateOrderRequest request){

        Optional<IdempotencyRecord> existing = idempotencyRepository.findById(key);

        if(existing.isPresent()){
            log.info("Duplicate record for the key: {}", key);
            return deserialize(existing.get().getResponseBody());
        }

        if(request.getItems() == null || request.getItems().isEmpty()){
            throw new IllegalArgumentException("Order items cannot be empty");
        }

        Order order = new Order();

        Double price = 0d;

        for(OrderItemRequest req : request.getItems()){
            Product product = productRepository.findByIdWithUpdate(req.getProductId());

            if(product == null)
                throw new ObjectNotFoundException("No product with this id: "+req.getProductId());

            if(product.getStock() < req.getQuantity())
                throw new InsufficientStockException("Required stock not available for id: "+req.getProductId());

            product.setStock(product.getStock() - req.getQuantity());

            price += req.getQuantity() * product.getPrice();

            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantity(req.getQuantity())
                    .price(product.getPrice())
                    .order(order)
                    .build();

            order.getItems().add(item);
        }

        order.setTotalAmount(price);

        Order saved = orderRepository.save(order);
        OrderResponse response = orderMapper.toDto(saved);

        // ✅ Save idempotency record
        IdempotencyRecord record = IdempotencyRecord.builder()
                .idempotencyKey(key)
                .responseBody(serialize(response))
                .statusCode(HttpStatus.CREATED.value())
                .build();

        idempotencyRepository.save(record);

        return response;
    }

    private OrderResponse deserialize(String json) {
        try {
            return new ObjectMapper().readValue(json, OrderResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Deserialization failed");
        }
    }

    private String serialize(OrderResponse response) {
        try {
            return new ObjectMapper().writeValueAsString(response);
        } catch (Exception e) {
            throw new RuntimeException("Serialization failed");
        }
    }
}
