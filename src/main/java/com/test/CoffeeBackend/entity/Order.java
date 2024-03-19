package com.test.CoffeeBackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Time orderedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private AppUser customer;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "queue_id")
    private Queue queue;

    public Order(List<Product> products) {
        this.products = products;
        this.products.forEach(x -> x.getOrders().add(this));
    }
}
