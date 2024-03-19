package com.test.CoffeeBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @Schema(description = "id of products in this order", type = "[int]", example = "[0,1]")
    private List<Integer> productOrdered;
    @Schema(description = "person who ordered this", type = "int", example = "0")
    private Integer userId;
    @Schema(description = "which queue this order is in", type = "int", example = "0")
    private Integer queueId;
}
