package com.unyx.ticketeira.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
@EqualsAndHashCode(of = "id")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @Column(nullable = false)
    private String provider;
    @Column(name = "provider_payment_id")
    private String providerPaymentId;
    @Column(nullable = false)
    private String method;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private String currency = "BRL";
    @Column(nullable = false)
    private String status;
    @Column(name = "payment_data", columnDefinition = "json")
    private String paymentData;
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}