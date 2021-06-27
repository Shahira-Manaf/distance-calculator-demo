package com.myproject.distancedemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.distancedemo.model.PostcodeDtl;

@SpringBootTest
@AutoConfigureMockMvc
public class AppTest 
{
	private String TEST_POSTCODE = "TEST POSTCODE";
	
	private String TEST_POSTCODE_2 = "TEST POSTCODE 2";
	
	@Autowired
    private MockMvc mockMvc;
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public static String replaceString(String value) {
		String newString = value.replace(" ", "%20");
		return newString;
	}
	
	@Test
	public void contextLoader() throws Exception {
		basicAuth();
		forbiddenUser();
		createPostcode();
		findAll();
		findPostcode();
		updatePostcode();
		deletePostcode();
	}

	public void basicAuth() throws Exception {
	    this.mockMvc
	            .perform(get("/findAll"))
	            .andExpect(status().isUnauthorized());
	}
	
	public void forbiddenUser() throws Exception {
		this.mockMvc.perform(post("/createPostcode").header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("user:password".getBytes()))
				.content(asJsonString(new PostcodeDtl(TEST_POSTCODE, 80.123, -20.356)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isForbidden());
	}
	
	public void findAll() throws Exception {
	    this.mockMvc
	            .perform(get("/findAll").header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("user:password".getBytes())))
	            .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$._embedded.postcodeDtlResponseList").exists())
	            .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.postcodeDtlResponseList[*].postcode").isNotEmpty());
	}
	
		public void findPostcode() throws Exception {
		    this.mockMvc
		            .perform(get("/findByPostcode/" + TEST_POSTCODE).header(HttpHeaders.AUTHORIZATION,
		                    "Basic " + Base64Utils.encodeToString("user:password".getBytes())))
		            .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.postcode").exists())
		            .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").exists())
		            .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").exists());
		}
	
	public void createPostcode() throws Exception {		
		this.mockMvc.perform(post("/createPostcode").header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("admin:password".getBytes()))
				.content(asJsonString(new PostcodeDtl(TEST_POSTCODE, 50.138, -2.546)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.postcode").value(TEST_POSTCODE))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(50.138))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(-2.546));
	
		this.mockMvc.perform(post("/createPostcode").header(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64Utils.encodeToString("admin:password".getBytes()))
				.content(asJsonString(new PostcodeDtl(TEST_POSTCODE_2, 51.987, -2.259)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.postcode").value(TEST_POSTCODE_2))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(51.987))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(-2.259));
	}
	
	
	  public void updatePostcode() throws Exception {
		  this.mockMvc.perform(put("/updateByPostcode/" + TEST_POSTCODE).header(HttpHeaders.AUTHORIZATION,
	                "Basic " + Base64Utils.encodeToString("admin:password".getBytes())).content(asJsonString(new
				  PostcodeDtl(TEST_POSTCODE, 52.125, -2.612)))
		  .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		  .andExpect(status().isCreated())
		  .andExpect(MockMvcResultMatchers.jsonPath("$.postcode").value(TEST_POSTCODE))
		  .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(52.125))
		  .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(-2.612));
	  }
	 
	  public void deletePostcode() throws Exception {
		  this.mockMvc.perform(delete("/deleteByPostcode/" + TEST_POSTCODE).header(HttpHeaders.AUTHORIZATION,
	                "Basic " + Base64Utils.encodeToString("admin:password".getBytes())));
		  
		  this.mockMvc.perform(delete("/deleteByPostcode/" + TEST_POSTCODE_2).header(HttpHeaders.AUTHORIZATION,
	                "Basic " + Base64Utils.encodeToString("admin:password".getBytes())));
	  }
	  
	  public void findDistance() throws Exception {
		  this.mockMvc.perform(put("/findDistance").header(HttpHeaders.AUTHORIZATION,
	                "Basic " + Base64Utils.encodeToString("user:password".getBytes()))
		  .param("postcodeStr1", TEST_POSTCODE)
          .param("postcodeStr2", TEST_POSTCODE_2))
		  .andExpect(status().isOk())
		  .andExpect(MockMvcResultMatchers.jsonPath("$.distance").value(70));
	  }
}
