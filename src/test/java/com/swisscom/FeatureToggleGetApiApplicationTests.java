package com.swisscom;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swisscom.model.payload.PaginatedFeatureToggleResponse;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class FeatureToggleGetApiApplicationTests {

	@Value("${app.data.items.per.page}")
	private int pageSize ; 
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Test
	@Order(1)
	void sholdReturnOkAndFeatureToggleShoudBeGraterThanZeroWhenRequestedWithDefaultValues() throws Exception {

		MvcResult result = this.mockMvc
				.perform(get("/api/featuretoggle").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		PaginatedFeatureToggleResponse createdFeatureToggle = mapper.readValue(contentAsString, PaginatedFeatureToggleResponse.class);
		assertThat("List Size ", (long) createdFeatureToggle.getPayload().size(), greaterThan(0l));

	}

	@Test
	@Order(2)
	void sholdReturnDefaultResultsPerpageWithDefaultValues() throws Exception {

		MvcResult result = this.mockMvc
				.perform(get("/api/featuretoggle").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		PaginatedFeatureToggleResponse createdFeatureToggle = mapper.readValue(contentAsString, PaginatedFeatureToggleResponse.class);
		assertEquals(pageSize,createdFeatureToggle.getDataPerPage());
	}
	
	@Test
	@Order(3)
	void sholdReturnRequestedResultsPageWithMatchingDataCount() throws Exception {

		MvcResult result = this.mockMvc
				.perform(get("/api/featuretoggle").param("page", "0").param("pageSize","3").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		PaginatedFeatureToggleResponse createdFeatureToggle = mapper.readValue(contentAsString, PaginatedFeatureToggleResponse.class);
		assertEquals(3,createdFeatureToggle.getDataPerPage());
		assertEquals(3,createdFeatureToggle.getPayload().size());
	}
	
	@Test
	@Order(4)
	void sholdReturnNoContentErrorRequestedWhenUnavaialblePageRequested() throws Exception {

	this.mockMvc
				.perform(get("/api/featuretoggle").param("page", "9999").param("pageSize","3").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNoContent());
	
	

		
	}
	@Test
	@Order(5)
	void shouldMatchResultsCountAndResponceResultsCountWhileIteratingToEnd() throws Exception {


		
		long currentPage = 0 ;
		long countedResults  = 0 ;
		long resultsCountFromResponse  = 0 ; 
		long totalpages = 0 ; 
		boolean pagesAvaialable = true ;
		
		while( pagesAvaialable) {
			MvcResult result =  this.mockMvc
			.perform(get("/api/featuretoggle").param("page", String.valueOf(currentPage)).param("pageSize","5").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andDo(print()).andReturn();
			
			pagesAvaialable = result.getResponse().getStatus() == 200 ;
			if(pagesAvaialable) {
				
				String contentAsString = result.getResponse().getContentAsString();
				PaginatedFeatureToggleResponse createdFeatureToggle = mapper.readValue(contentAsString, PaginatedFeatureToggleResponse.class);
				countedResults += createdFeatureToggle.getPayload().size();
				
				
				resultsCountFromResponse  = createdFeatureToggle.getTotalCount() ; 
				totalpages = createdFeatureToggle.getTotalPages();
				currentPage ++ ; 
			}
			
		}
		
		assertEquals(totalpages, currentPage);
		assertEquals(countedResults, resultsCountFromResponse);
	

		
	}
}
