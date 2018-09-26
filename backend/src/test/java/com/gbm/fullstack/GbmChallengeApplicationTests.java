package com.gbm.fullstack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import com.gbm.fullstack.controller.ProductController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GbmChallengeApplicationTests {
	@Autowired
	 private ProductController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
