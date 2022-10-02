package com.swisscom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

class FeatureTogglePostApiApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Test
	void shouldReturnCreatedObjectAndResturnWithIdOnPost() throws Exception {
		//dd/MM/yyyy HH:mm:ss 31/12/2012 23:59:59
		String creationJson = """
				{
					  "displayName": "My Feature A",
					  "technicalName": "my-feature-a",
					  "expiresOn": "31/12/2022 23:59:59",
					  "description": "My Feature A Sample Description",
					  "inverted": false
					}
				""";
				
		this.mockMvc
				.perform(post("/api/featuretoggle").content(creationJson).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists());

	}
	@Test
	void shouldReturnOkObjectAndResturnWithIdOnPatch() throws Exception {
		//dd/MM/yyyy HH:mm:ss 31/12/2012 23:59:59
		String creationJson = """
				{	"id" : "1" ,
					  "displayName": "My Feature g",
					  "technicalName": "my-feature-g",
					  "expiresOn": "31/12/2022 23:59:59",
					  "description": "My Feature A Sample Description",
					  "inverted": false
					}
				""";
				
		this.mockMvc
				.perform(patch("/api/featuretoggle/1").content(creationJson).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());

	}
	@Test
	void shouldReturnBadRequestErrorWhenCreatingWithoutTechinicalNameOnPost() throws Exception {
		//dd/MM/yyyy HH:mm:ss 31/12/2012 23:59:59
		String creationJson = """
				{
					  "displayName": "My Feature B",
					  "technicalName": "",
					  "expiresOn": "31/12/2022 23:59:59",
					  "description": "My Feature B Sample Description",
					  "inverted": false
					}
				""";
				
		this.mockMvc
				.perform(post("/api/featuretoggle").content(creationJson).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString("FeatureToggle Technical Name Should Not Be Empty")));

	}
	
	@Test
	void shouldReturnBadRequestErrorWhenCreatingWithoutDisplayNameOnPost() throws Exception {
		//dd/MM/yyyy HH:mm:ss 31/12/2012 23:59:59
		String creationJson = """
				{
					  "displayName": "",
					  "technicalName": "my-feature-a",
					  "expiresOn": "31/12/2022 23:59:59",
					  "description": "My Feature A Sample Description",
					  "inverted": false
					}
				""";
				
		this.mockMvc
				.perform(post("/api/featuretoggle").content(creationJson).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString("FeatureToggle Name Should Not Be Empty")));

	}
	
	
	@Test
	void shouldReturnBadRequestErrorWhenCreatingWithBadDateFromatExpiredOnOnPost() throws Exception {
		//dd/MM/yyyy HH:mm:ss 31/12/2012 23:59:59
		String creationJson = """
				{
					  "displayName": "My Feature C",
					  "technicalName": "my-feature-c",
					  "expiresOn": "31/12/20 ",
					  "description": "My Feature A Sample Description",
					  "inverted": false
					}
				""";
				
		this.mockMvc
				.perform(post("/api/featuretoggle").content(creationJson).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString("Invalid date format , Please send data with dd/MM/yyyy HH:mm:ss format")));

	}
	
	
	@Test
	void shouldReturnConflitWhenSameDisplayNameEntredTiceOnPost() throws Exception {
		//dd/MM/yyyy HH:mm:ss 31/12/2012 23:59:59
		String creationJson = """
				{
					  "displayName": "My Feature D",
					  "technicalName": "my-feature-d",
					  "expiresOn": "31/12/2022 23:59:59",
					  "description": "My Feature A Sample Description",
					  "inverted": false
					}
				""";
				
		this.mockMvc
				.perform(post("/api/featuretoggle").content(creationJson).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists());
		
		creationJson = """
				{
					  "displayName": "My Feature D",
					  "technicalName": "my-feature-e",
					  "expiresOn": "31/12/2022 23:59:59",
					  "description": "My Feature A Sample Description",
					  "inverted": false
					}
				""";
		
		this.mockMvc
		.perform(post("/api/featuretoggle").content(creationJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isConflict()).andExpect(content().string(containsString("FeatureToggle Display Name should be unique")));

	}
	
	@Test
	void shouldReturnConflitWhenSameTechnicalNameEntredTiceOnPost() throws Exception {
		//dd/MM/yyyy HH:mm:ss 31/12/2012 23:59:59
		String creationJson = """
				{
					  "displayName": "My Feature E",
					  "technicalName": "my-feature-e",
					  "expiresOn": "31/12/2022 23:59:59",
					  "description": "My Feature A Sample Description",
					  "inverted": false
					}
				""";
				
		this.mockMvc
				.perform(post("/api/featuretoggle").content(creationJson).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists());
		
		creationJson = """
				{
					  "displayName": "My Feature F",
					  "technicalName": "my-feature-e",
					  "expiresOn": "31/12/2022 23:59:59",
					  "description": "My Feature A Sample Description",
					  "inverted": false
					}
				""";
		
		this.mockMvc
		.perform(post("/api/featuretoggle").content(creationJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isConflict()).andExpect(content().string(containsString("FeatureToggle Technical Name should be unique")));

	}
}
