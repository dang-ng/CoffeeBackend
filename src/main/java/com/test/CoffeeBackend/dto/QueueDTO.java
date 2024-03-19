package com.test.CoffeeBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueDTO {
    @Schema(description = "queue id", type = "int", example = "0")
    private Integer id;
    @Schema(description = "orders in this queue", type = "[int]", example = "[0,1]")
    private List<OrderResponseDTO> orderList;
}
