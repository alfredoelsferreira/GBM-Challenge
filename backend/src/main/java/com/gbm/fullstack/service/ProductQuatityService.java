package com.gbm.fullstack.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gbm.fullstack.model.Product;
import com.gbm.fullstack.model.ProductQuantity;
import com.gbm.fullstack.repository.ProductQuantityRepository;


@Service
public class ProductQuatityService implements IProductQuatityService, IObserver {
	
	@Autowired
	private ProductQuantityRepository productQuantityRepository;

	@Override
	public ProductQuantity addProductQuantity(Product product) {
		ProductQuantity productQuantity = new ProductQuantity();
		productQuantity.setProduct(product);
		productQuantity.setNewQuantity(product.getQuantity());
				
		ProductQuantity existingProductQuantity = productQuantityRepository.findFirstByProductOrderByLastModifiedDate(product);
		if (existingProductQuantity == null || !existingProductQuantity.getNewQuantity().equals(product.getQuantity())) {
			productQuantityRepository.saveAndFlush(productQuantity);
		}
		
		return productQuantity;
	}

	@Override
	public void update(Object data) {
		Product product = (Product) data;
		
		if (product != null) {
			addProductQuantity(product);
		}
		
	}

}
