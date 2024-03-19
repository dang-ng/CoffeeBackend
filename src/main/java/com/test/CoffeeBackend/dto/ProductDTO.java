package com.test.CoffeeBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO
{
    @Schema(description = "link of product image", type = "string", example = "https://picsum.photos/300/300")
    private String image;
    @Schema(description = "name of product", type = "string", example = "Espresso Coffee")
    private String name;
    @Schema(description = "price of product", type = "float", example = "2.99")
    private float price;
    @Schema(description = "description of product", type = "string", example = "With a deep, rich flavour and very smooth, this traditional recipe has a uniquely Italian coffee aroma")
    private String description;
}
