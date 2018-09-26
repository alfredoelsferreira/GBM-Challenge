package com.gbm.fullstack.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gbm.fullstack.dto.ProductDTO;
import com.gbm.fullstack.model.Product;

public interface IProductService {
	Page<Product> getAllProducts(Pageable pageable);
	
	Page<Product> findByName(String name, Pageable pageable);
	
	Product getProductById(UUID productId);
	
	Product addProduct(ProductDTO productDTO);
	
	Product updateProduct(UUID productId, ProductDTO productDTO);
	
	Product patchProduct(UUID productId, ProductDTO productDTO);
	
	Product deleteProduct(UUID productId);
}
