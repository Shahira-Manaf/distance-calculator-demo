package com.myproject.distancedemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

@SpringBootTest
@AutoConfigureMockMvc
public class AppTest 
{
	@Autowired
    private MockMvc mockMvc;

	@Test
	public void basicAuth() throws Exception {
	    this.mockMvc
	            .perform(get("/findAll"))
	            .andExpect(status().isUnauthorized());
	}
	
	@Test
	public void findAll() throws Exception {
	    this.mockMvc
	            .perform(get("/findByPostcode/AB10 1XG").header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("user:password".getBytes())))
	            .andExpect(status().isOk());
	    
	    // response.andExpect(jsonPath("$.your.object.path", is("your_expected_output")));
	}
}
