package com.adaland.springsecurity.controller;


import com.adaland.springsecurity.model.dao.Product;
import com.adaland.springsecurity.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    @GetMapping
    public ResponseEntity<String> getProduct() {
        return ResponseEntity.ok("Hello from this secured endpoint");
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                productRepository.save(product)
        );
    }
}
