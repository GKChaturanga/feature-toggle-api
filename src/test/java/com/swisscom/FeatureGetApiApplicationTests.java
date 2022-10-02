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
import com.swisscom.model.payload.FeatureDisplay;
import com.swisscom.model.payload.FeatureResponse;
import com.swisscom.model.payload.FetureAttachRequest;
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

class FeatureGetApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Test
	@Order(1)
	void sholdReturnOkAndFeatureShoudBeTwoWhenRequestedWithTwoGivenInputValues() throws Exception {
		String input = """

							{
				  "customerId": 1,
				  "features": [
				    "my-feature-p",
				    "my-feature-q"
				  ]
				}

								""";

		MvcResult result = this.mockMvc.perform(post("/api/features").content(input)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		FeatureResponse createdFeatureToggle = mapper.readValue(contentAsString, FeatureResponse.class);
		assertThat("List Size ", createdFeatureToggle.features().size(), is(02));

	}

	@Test
	@Order(1)
	void sholdAttachAndDetachFromAttachAndDetachCalls() throws Exception {
		// Retrieving features
		String input = """

							{
				  "customerId": 1,
				  "features": [
				    "my-feature-p",
				    "my-feature-q"
				  ]
				}

								""";

		MvcResult result = this.mockMvc.perform(post("/api/features").content(input)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		FeatureResponse createdFeatureToggle = mapper.readValue(contentAsString, FeatureResponse.class);
		// Test for the list size
		assertThat("List Size ", createdFeatureToggle.features().size(), is(02));

		for (FeatureDisplay fd : createdFeatureToggle.features()) {
			// Test for customer attached
			assertThat("Customer Attached ", fd.customerFound(), is(false));

			input = """

								{
					  "customerId": 1,
					  "featureId": %d
					}

									""".formatted(fd.id());
			;
			// Update attachment
			result = this.mockMvc
					.perform(post("/api/customer/attach").content(input).contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON))
					.andDo(print()).andExpect(status().isCreated()).andReturn();

		}

		// Retrieving again

		input = """

							{
				  "customerId": 1,
				  "features": [
				    "my-feature-p",
				    "my-feature-q"
				  ]
				}

								""";

		result = this.mockMvc.perform(post("/api/features").content(input).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();

		contentAsString = result.getResponse().getContentAsString();
		createdFeatureToggle = mapper.readValue(contentAsString, FeatureResponse.class);
		assertThat("List Size ", createdFeatureToggle.features().size(), is(02));

		for (FeatureDisplay fd : createdFeatureToggle.features()) {
			// Test for the attachment
			assertThat("Customer Attached ", fd.customerFound(), is(true));

		}
	}
}
