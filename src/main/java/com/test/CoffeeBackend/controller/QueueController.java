package com.test.CoffeeBackend.controller;

import com.test.CoffeeBackend.service.impl.QueueServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queues")
@SecurityRequirement(name = "Authorization")
public class QueueController
{
    final QueueServiceImpl queueService;

    public QueueController(QueueServiceImpl queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/shop/{id}")
    public ResponseEntity<?> getAllQueuesInShop(@PathVariable Integer id)
    {
        return queueService.getAllQueuesInShop(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllOrdersInCurrentQueue(@PathVariable Integer id)
    {
        return queueService.getOrdersInQueue(id);
    }
}
