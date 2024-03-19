package com.test.CoffeeBackend.service.impl;

import com.test.CoffeeBackend.dto.OrderResponseDTO;
import com.test.CoffeeBackend.dto.ProductDTO;
import com.test.CoffeeBackend.dto.QueueDTO;
import com.test.CoffeeBackend.entity.Queue;
import com.test.CoffeeBackend.repository.QueueRepository;
import com.test.CoffeeBackend.repository.ShopRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QueueServiceImpl
{
    final QueueRepository queueRepository;
    final ModelMapper modelMapper;
    final ShopRepository shopRepository;
    public QueueServiceImpl(QueueRepository queueRepository, ModelMapper modelMapper, ShopRepository shopRepository) {
        this.queueRepository = queueRepository;
        this.modelMapper = modelMapper;
        this.shopRepository = shopRepository;
    }

    public ResponseEntity<?> getAllQueuesInShop(Integer id)
    {
        List<Queue> queues = queueRepository.findAllByShopId(id);
        ArrayList<QueueDTO> queueDTOs = new ArrayList<>();

        queues.forEach(queue -> {
            GetOrderList(queueDTOs, queue);
        });

        return ResponseEntity.ok(queueDTOs);
    }

    public ResponseEntity<?> getOrdersInQueue(Integer id)
    {
        Optional<Queue> queueOptional = queueRepository.findById(id);
        if(queueOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("queue not found");
        }
        Queue queue = queueOptional.get();
        ArrayList<QueueDTO> queueDTOs = new ArrayList<>();
        GetOrderList(queueDTOs, queue);
        return ResponseEntity.ok(queueDTOs);
    }


    private void GetOrderList(ArrayList<QueueDTO> queueDTOs, Queue queue) {
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        queue.getOrders().forEach(order -> {
            List<ProductDTO> productDTOList = new ArrayList<>();
            order.getProducts().forEach(product -> productDTOList.add(modelMapper.map(product, ProductDTO.class)));
            OrderResponseDTO responseDTO = new OrderResponseDTO(order.getId(), order.getQueue().getId(), productDTOList);
            orderResponseDTOList.add(responseDTO);
        });
        var queueDTO = new QueueDTO();
        queueDTO.setId(queue.getId());
        queueDTO.setOrderList(orderResponseDTOList);
        queueDTOs.add(queueDTO);
    }
}
