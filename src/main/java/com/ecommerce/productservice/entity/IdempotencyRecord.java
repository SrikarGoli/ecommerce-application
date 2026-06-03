package com.ecommerce.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="idempotency_record")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdempotencyRecord {

    @Id
    private String idempotencyKey;

    @Column(columnDefinition = "Text")
    private String responseBody;

    private Integer statusCode;
}
