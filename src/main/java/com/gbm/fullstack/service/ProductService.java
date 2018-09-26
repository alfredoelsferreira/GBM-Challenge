package com.gbm.fullstack.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gbm.fullstack.dto.ProductDTO;
import com.gbm.fullstack.model.Product;
import com.gbm.fullstack.repository.ProductRepository;
import com.gbm.fullstack.util.MapperUtil;

@Service
public class ProductService implements IProductService, ISubject{

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired // Spring's dependency injection
	private ProductRepository productRepository;
	
	private List<IObserver> observers = new ArrayList<IObserver>();
	
	private Product product;
	
	
	
	/**
	 * 
	 */
	@Override
	public Page<Product> getAllProducts(Pageable pageable)
	{
		return productRepository.findAll(pageable);
	}

	/**
	 * 
	 */
	@Override
	public Product getProductById(UUID productId) {
		Optional<Product> product =  productRepository.findById(productId);
		if (product.isPresent()) {
			return product.get();
		}
		
		return null;
	}

	/**
	 * 
	 */
	@Override
	public Product addProduct(ProductDTO productDTO) {
		product = productDTO.toModel();
		productRepository.saveAndFlush(product);
		notifyObservers();
		return product;
	}

	/**
	 * 
	 */
	@Override
	public Product updateProduct(UUID productId,ProductDTO productDTO) {
		Product existingProduct = getProductById(productId);
		
		if (existingProduct != null) {
			Product newProduct = productDTO.toModel().setProductId(productId);
			
			MapperUtil.copyNonNullProperties(newProduct, existingProduct);
			product = productRepository.saveAndFlush(existingProduct);
			notifyObservers();
			return product;
		}
		
		return null;
	}

	/**
	 * 
	 */
	@Override
	public Product patchProduct(UUID productId,ProductDTO productDTO) {
		return updateProduct(productId, productDTO);
	}

	/**
	 * 
	 */
	@Override
	public Product deleteProduct(UUID productId) {
		Product product = getProductById(productId);
		
		if (product != null) {
			productRepository.deleteById(productId);
		}
		
		return product;
	}

	@Override
	public Page<Product> findByName(String name, Pageable pageable) {
		return productRepository.findByName(name, pageable);
	}

	@Override
	public void addObserver(IObserver ob) {
		observers.add(ob);	
	}

	@Override
	public void removeObserver(IObserver ob) {
		observers.remove(ob);
		
	}

	@Override
	public void notifyObservers() {
		for (IObserver observer : observers) {
            observer.update(product);
		}
		
	}

}
