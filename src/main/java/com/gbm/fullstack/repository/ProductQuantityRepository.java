package com.gbm.fullstack.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbm.fullstack.model.Product;
import com.gbm.fullstack.model.ProductQuantity;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, UUID> {
	ProductQuantity findFirstByProductOrderByLastModifiedDate(Product product);
}
