package com.gbm.fullstack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

import com.gbm.fullstack.repository.ProductRepository;
import com.gbm.fullstack.repository.UserRepository;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { FreeMarkerAutoConfiguration.class })
public class GbmChallengeApplication {
	private static final Logger logger = LoggerFactory.getLogger(GbmChallengeApplication.class);
	
	@Autowired
	private ProductRepository porductRepository;
	
	@Autowired	
	private UserRepository urepository;	
	

	public static void main(String[] args) {
		SpringApplication.run(GbmChallengeApplication.class, args);
		logger.info("Starting GBM Challenge");
	}
}
