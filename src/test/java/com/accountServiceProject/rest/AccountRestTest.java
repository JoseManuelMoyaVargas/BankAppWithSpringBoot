package com.accountServiceProject.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class AccountRestTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testGetMain() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON_VALUE);
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
		
	}

	

}
