package com.adaland.springsecurity.repository;

import com.adaland.springsecurity.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
