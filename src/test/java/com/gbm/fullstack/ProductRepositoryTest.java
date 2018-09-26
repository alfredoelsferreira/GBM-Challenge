package com.gbm.fullstack;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.gbm.fullstack.model.Product;
import com.gbm.fullstack.repository.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ProductRepository repository;
	
	@Test
	public void saveProduct() {
		Product product = new Product("Sofa", "Big Sofa", 2000.0, 10);
		entityManager.persistAndFlush(product);
		
		assertThat(product.getProductId()).isNotNull();
	}
	
	@Test
	public void deleteAllProducts() {
		entityManager.persistAndFlush(new Product("Sofa", "Big Sofa", 2000.0, 10));
		entityManager.persistAndFlush(new Product("Taable", "Big Table", 1000.0, 20));
		
		repository.deleteAll();
		assertThat(repository.findAll()).isEmpty();
	}

}
