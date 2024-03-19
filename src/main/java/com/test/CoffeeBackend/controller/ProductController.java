package com.test.CoffeeBackend.controller;

import com.test.CoffeeBackend.dto.ProductDTO;
import com.test.CoffeeBackend.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "Authorization")
public class ProductController
{
    final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping("/add")

    public ResponseEntity<?> addNewProduct(@RequestBody ProductDTO productDTO)
    {
        return productService.createProduct(productDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id)
    {
        return productService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id)
    {
        return productService.deleteProduct(id);
    }
}
