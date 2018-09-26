package com.gbm.fullstack;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.gbm.fullstack.model.Product;
import com.gbm.fullstack.repository.ProductRepository;
import com.gbm.fullstack.service.ProductService;

@RunWith(SpringRunner.class)
public class ProductServiceIntegrationTest {
	@TestConfiguration
    static class ProductServiceIntegrationTestContextConfiguration {
  
        @Bean
        public ProductService productService() {
            return new ProductService();
        }
    }
 
    @Autowired
    private ProductService productService;
 
    @MockBean
    private ProductRepository productRepository;
 
    @Before
    public void setUp() {
        Product sofa = new Product("Sofa", "Big Sofa", 2000.0, 10);
     
        Mockito.when(productRepository.getOne(sofa.getProductId()))
          .thenReturn(sofa);
    }
    
    @Test
    public void whenValidName_thenProductShouldBeFound() {
        String name = "Sofa";
        Pageable pageable = new PageRequest(0, 10);
        Page<Product> page = productService.findByName(name, pageable);
      
         assertThat(page.getContent().get(0).getName())
          .isEqualTo(name);
     }

}
