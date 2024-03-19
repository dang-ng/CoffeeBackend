package com.test.CoffeeBackend.controller;

import com.test.CoffeeBackend.dto.OrderDTO;
import com.test.CoffeeBackend.service.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@SecurityRequirement(name = "Authorization")
public class OrderController
{
    final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createNewOrder(@RequestBody OrderDTO orderDTO)
    {
        try {
            return orderService.createOrder(orderDTO);
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer id)
    {
        return orderService.getOrder(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> getAllProducts(@PathVariable Integer id)
    {
        return orderService.deleteOrder(id);
    }


}