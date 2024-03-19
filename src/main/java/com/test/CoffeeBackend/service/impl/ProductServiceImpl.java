package com.test.CoffeeBackend.service.impl;

import com.test.CoffeeBackend.dto.ProductDTO;
import com.test.CoffeeBackend.entity.Product;
import com.test.CoffeeBackend.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class ProductServiceImpl
{
    final ProductRepository productRepository;
    final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<?> createProduct(ProductDTO productDTO)
    {
       Product productEntity =
               new Product(productDTO.getImage(), productDTO.getName(),productDTO.getPrice(), productDTO.getDescription());
       productRepository.save(productEntity);
       URI uri =
               URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
                       path("/api/products/add").toUriString());
       return ResponseEntity.created(uri).body("saved");
    }

    public ResponseEntity<?> getProduct(Integer id)
    {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent())
        {
            ProductDTO productDTO = modelMapper.map(product.get(), ProductDTO.class);
            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> deleteProduct(Integer id)
    {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent())
        {
            productRepository.delete(product.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
