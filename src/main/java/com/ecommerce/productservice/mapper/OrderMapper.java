package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.OrderItemResponse;
import com.ecommerce.productservice.dto.OrderResponse;
import com.ecommerce.productservice.entity.Order;
import com.ecommerce.productservice.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderResponse toDto(Order order){
        if(order==null) return null;
        OrderResponse obj=new OrderResponse();
        obj.setId(order.getId());
        obj.setTotalAmount(order.getTotalAmount());
        List< OrderItemResponse> items=order.getItems()
                .stream()
                .map(this::mapItem)
                .toList();
        obj.setItems(items);
        return obj;
    }


    private OrderItemResponse mapItem(OrderItem item) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.setProductId(item.getProduct().getId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }

}
