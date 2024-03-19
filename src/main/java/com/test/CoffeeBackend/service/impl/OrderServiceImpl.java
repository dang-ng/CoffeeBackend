package com.test.CoffeeBackend.service.impl;

import com.test.CoffeeBackend.dto.OrderDTO;
import com.test.CoffeeBackend.dto.OrderResponseDTO;
import com.test.CoffeeBackend.dto.ProductDTO;
import com.test.CoffeeBackend.entity.Order;
import com.test.CoffeeBackend.repository.OrderRepository;
import com.test.CoffeeBackend.repository.ProductRepository;
import com.test.CoffeeBackend.repository.QueueRepository;
import com.test.CoffeeBackend.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

@Service
public class OrderServiceImpl {
    final OrderRepository orderRepository;
    final UserRepository userRepository;
    final ProductRepository productRepository;
    final QueueRepository queueRepository;
    final ModelMapper modelMapper;
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, QueueRepository queueRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.queueRepository = queueRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<?> createOrder(OrderDTO orderDTO) throws BadRequestException {
        var products = productRepository
                .findAllById(orderDTO.getProductOrdered());

        Order order = new Order(products);

        order.setCustomer(userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new BadRequestException("User id not found")));

        order.setQueue(queueRepository
                .findById(orderDTO.getQueueId())
                .orElseThrow(() -> new BadRequestException("queue id not found")));

        order.setOrderedAt(Time.valueOf(LocalTime.now()));

        var result = orderRepository.save(order);
        List<ProductDTO> productDTOList = new ArrayList<>();
        products.forEach(product -> productDTOList.add(modelMapper.map(product, ProductDTO.class)));
        OrderResponseDTO responseDTO = new OrderResponseDTO(result.getId(), order.getQueue().getId(), productDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<?> getOrder(Integer id)
    {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("order not found");
        }
        var order = orderOptional.get();
        List<ProductDTO> productDTOList = new ArrayList<>();
        order.getProducts().forEach(product -> productDTOList.add(modelMapper.map(product, ProductDTO.class)));
        OrderResponseDTO responseDTO = new OrderResponseDTO(order.getId(), order.getQueue().getId(), productDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<?> deleteOrder(Integer id)
    {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent())
        {
            orderRepository.delete(order.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



}
