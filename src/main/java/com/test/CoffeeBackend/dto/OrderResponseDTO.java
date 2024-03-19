package com.test.CoffeeBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Integer orderId;
    private Integer queueId;
    private List<ProductDTO> productDTOList;
}
