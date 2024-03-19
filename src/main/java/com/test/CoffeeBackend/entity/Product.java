package com.test.CoffeeBackend.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "image")
    private String image;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "products")
    private List<Shop> shops;

    @JsonBackReference
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Order> orders;

    public Product(String image, String name, float price, String description) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
    }

}
