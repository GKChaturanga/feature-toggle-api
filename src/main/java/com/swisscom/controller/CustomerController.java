package com.swisscom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swisscom.conf.FeatureToggleException;
import com.swisscom.model.payload.CustomerOut;
import com.swisscom.model.payload.ErrorResponse;
import com.swisscom.model.payload.FeatureDisplay;
import com.swisscom.model.payload.FeatureRequest;
import com.swisscom.model.payload.FeatureToggleOut;
import com.swisscom.model.payload.FetureAttachRequest;
import com.swisscom.model.payload.PaginatedFeatureToggleResponse;
import com.swisscom.model.payload.PaginatedRequest;
import com.swisscom.service.CustomerService;
import com.swisscom.service.FeatureToggleService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
@OpenAPIDefinition(info = @Info(title = "Feature Toggles Application - by swisscom ", description = "Recrutement excersice", contact = @Contact(email = "kchaturanga@gmail.com", name = "Kasun Chaturanga Gajamange")))

public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@GetMapping
	@Operation(summary = "Get Customer data", description = "Returns paged response with request data  ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List<Customer> for requested page criteria , \n<br />  " ,
					   content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedFeatureToggleResponse.class)) }),
			@ApiResponse(responseCode = "204", description = "FeatureToggles not found for requested page criteria", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "API Internal error ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

	public ResponseEntity<List<CustomerOut>> getPagedFeatureToggles( )
			throws FeatureToggleException {
		return new ResponseEntity<>(customerService.listCustomers(), HttpStatus.OK);

	}
	
	@Autowired
	private FeatureToggleService featureToggleService;
	
	
	@PostMapping("/attach")
	@Operation(summary = "Creates a FeatureToggle ", description = "Creates a new FeatureToggle in the system ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Returns the successfully created FeatureToggle    ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureToggleOut.class)) }),
			@ApiResponse(responseCode = "409", description = "A FeatureToggleOut available with same name or technical in the system ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "API Internal error ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

	public ResponseEntity<FeatureDisplay> attachFeatureRequests(
			  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "FeatureRequest ", required = true, content = @Content(schema = @Schema(implementation = FeatureRequest.class))) @RequestBody FetureAttachRequest featureRequest)
			throws FeatureToggleException {
		return new ResponseEntity<>(customerService.attach(featureRequest), HttpStatus.CREATED);

	}
	@PostMapping("/detach")
	@Operation(summary = "Creates a FeatureToggle ", description = "Creates a new FeatureToggle in the system ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Returns the successfully created FeatureToggle    ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureToggleOut.class)) }),
			@ApiResponse(responseCode = "409", description = "A FeatureToggleOut available with same name or technical in the system ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "API Internal error ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

	public ResponseEntity<FeatureDisplay> deattachFeatureRequests(
			  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "FeatureRequest ", required = true, content = @Content(schema = @Schema(implementation = FeatureRequest.class))) @RequestBody FetureAttachRequest featureRequest)
			throws FeatureToggleException {
		return new ResponseEntity<>(customerService.detach(featureRequest), HttpStatus.CREATED);

	}
}
