package com.test.CoffeeBackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "queues")
@NoArgsConstructor
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "queue")
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private Integer maxQueueSize;
}
