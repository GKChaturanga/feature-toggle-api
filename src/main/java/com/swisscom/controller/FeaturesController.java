package com.swisscom.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swisscom.conf.FeatureToggleException;
import com.swisscom.model.payload.ErrorResponse;
import com.swisscom.model.payload.FeatureDisplay;
import com.swisscom.model.payload.FeatureRequest;
import com.swisscom.model.payload.FeatureResponse;
import com.swisscom.model.payload.FeatureToggleIn;
import com.swisscom.model.payload.FeatureToggleOut;
import com.swisscom.service.FeatureToggleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



@CrossOrigin
@RestController
@RequestMapping("/api/features")
public class FeaturesController {
	
	
	
	@Autowired
	private FeatureToggleService featureToggleService;
	
	
	@PostMapping
	@Operation(summary = "Creates a FeatureToggle ", description = "Creates a new FeatureToggle in the system ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Returns the successfully created FeatureToggle    ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureToggleOut.class)) }),
			@ApiResponse(responseCode = "409", description = "A FeatureToggleOut available with same name or technical in the system ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "API Internal error ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

	public ResponseEntity<FeatureResponse> findFeatureRequests(
			  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "FeatureRequest ", required = true, content = @Content(schema = @Schema(implementation = FeatureRequest.class))) @RequestBody FeatureRequest featureRequest)
			throws FeatureToggleException {
		return new ResponseEntity<>(featureToggleService.findFeatureRequests(featureRequest), HttpStatus.OK);

	}
}
