package com.mm.webstn.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "product_order")
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Instant date;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id_fk")
    private Product productIdFk;

}