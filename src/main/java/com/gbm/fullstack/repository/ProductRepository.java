package com.gbm.fullstack.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gbm.fullstack.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
	// Fetch Products by name
	Page<Product> findByName(String name, Pageable pageable);
}
