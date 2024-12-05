package com.mm.webstn.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "customerIdFk")
    private Set<MessageHistory> messageHistories = new LinkedHashSet<>();

    @OneToMany
    private Set<ProductOrder> productOrders = new LinkedHashSet<>();

    @OneToMany
    private Set<ProductShipment> productShipments = new LinkedHashSet<>();

}