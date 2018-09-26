package com.gbm.fullstack;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductRestTest {
	@Autowired
    private MockMvc mockMvc; //For testing the controllers or any endpoint that is exposed
	
	@Test
	public void testAuthentication() throws Exception {
		// Testing authentication with correct credentials
        this.mockMvc.perform(post("/v1/login").content("{\"username\":\"admin@test.com\", \"password\":\"admin\"}")).
        	andDo(print()).andExpect(status().isOk());

		// Testing authentication with wrong credentials
        this.mockMvc.perform(post("/v1/login").content("{\"username\":\"admin@test.com\", \"password\":\"wrongpwd\"}")).
        	andDo(print()).andExpect(status().is4xxClientError());
               
	}

}
