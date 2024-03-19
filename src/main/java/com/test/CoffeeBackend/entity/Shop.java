package com.test.CoffeeBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String shopName;

    @OneToMany(mappedBy = "shop")
    private List<Queue> queues;

    @ManyToMany
    @JoinTable(
            name = "shop_product",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    private String shopAddress;
}
