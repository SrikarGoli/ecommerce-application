package com.ecommerce.productservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private Double totalAmount;

    @PrePersist
    public void prePersist(){
        this.createdAt=LocalDateTime.now();
    }

    @OneToMany(mappedBy = "order",cascade=CascadeType.ALL)
    @JsonManagedReference
    @Builder.Default
    private List<OrderItem> items=new ArrayList<>();

}
