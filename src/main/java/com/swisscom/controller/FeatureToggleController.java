package com.swisscom.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swisscom.conf.FeatureToggleException;
import com.swisscom.model.payload.ErrorResponse;
import com.swisscom.model.payload.FeatureToggleIn;
import com.swisscom.model.payload.FeatureToggleOut;
import com.swisscom.model.payload.PaginatedFeatureToggleResponse;
import com.swisscom.model.payload.PaginatedRequest;
import com.swisscom.service.FeatureToggleService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@CrossOrigin
@RestController
@RequestMapping("/api/featuretoggle")
@OpenAPIDefinition(info = @Info(title = "Feature Toggles Application - by swisscom ", description = "Recrutement excersice", contact = @Contact(email = "kchaturanga@gmail.com", name = "Kasun Chaturanga Gajamange")))

public class FeatureToggleController {
	
	
	@Autowired
	private FeatureToggleService featureToggleService;

	/**Creates a new Feature Toggle in the system
	 * 
	 * @param featuretoggle
	 * @return ResponseEntity<FeatureToggle> 
	 * 	201  Returns the successfully created featuretoggle 
	 * 	409  A featuretoggle available with same technical name in the system
	 *  500	 API Internal error
	 * @throws FeatureToggleException
	 */
	@PostMapping
	@Operation(summary = "Creates a FeatureToggle ", description = "Creates a new FeatureToggle in the system ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Returns the successfully created FeatureToggle    ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureToggleOut.class)) }),
			@ApiResponse(responseCode = "409", description = "A FeatureToggleOut available with same name or technical in the system ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "API Internal error ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

	public ResponseEntity<FeatureToggleOut> createFeatureToggle(
			@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "FeatureToggleOut to save , FeatureToggleOut Name , TechnicalName  , Should not be empty and should not duplicate , expiredOn should be on dd/MM/yyyy HH:mm:ss format ", required = true, content = @Content(schema = @Schema(implementation = FeatureToggleIn.class))) @RequestBody FeatureToggleIn featuretoggle)
			throws FeatureToggleException {
		return new ResponseEntity<>(featureToggleService.createFeatureToggle(featuretoggle), HttpStatus.CREATED);

	}
	
 
	/**Returns FeatureToggleOut objects for given FeatureToggle ID 
	 * @GetMapping("/{featureToggleId}")
	 * @param featureToggleId
	 * @return ResponseEntity<FeatureToggleOut>  
	 * 	200 - Found the featureToggle for given Id
	 *  404 - FeatureToggle not found for given id
	 *  500 - API Internal error
	 * @throws FeatureToggleException
	 */
	@GetMapping("/{featureToggleId}")
	@Operation(summary = "Get featureToggle by id ", description = "Returns FeatureToggleOut objects for given FeatureToggle ID ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the featureToggle for given Id ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureToggleOut.class)) }),
			@ApiResponse(responseCode = "404", description = "FeatureToggle not found for given id ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "API Internal error ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

	public ResponseEntity<FeatureToggleOut> getFeatureToggleById(
			@Parameter(description = "ID of the featureToggle object ", required = true) @PathVariable long featureToggleId)
			throws FeatureToggleException {
		return new ResponseEntity<>(featureToggleService.getFeatureToggle(featureToggleId), HttpStatus.OK);
	}

	
	/**Returns paged response with request data and page meta data
	 * 
	 * @param page
	 * 	Number of the requested page ,
   	 *		Pages will be starting from 0 ,
   	 *		If the page is -1 or not available , One page will return with all available featureToggles
	 * @param pageSize
	 * 		Number of featureToggles per page ,
	 *	   If number is 0 or not available ,
	 *		   then system default number of results per page  will be considered ,
	 *		   If requested page (Page number ) is -1
	 *		    then this value  will be ignored
	 * @return ResponseEntity<PaginatedFeatureToggleResponse>
	 * 	200 Returns  PaginatedFeatureToggleResponse for requested page criteria , 
	 * 		PaginatedFeatureToggleResponse contains 
	 * 				the requested paged  data list  , 
	 * 				Total number of data available  , 
	 * 				How many items per page 
	 *  204 FeatureToggles not found for requested page criteria
	 *  500 API Internal error
	 * @throws FeatureToggleException
	 */
	@GetMapping
	@Operation(summary = "Get Paged FeatureToggle data", description = "Returns paged response with request data and page meta data ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = """  
						PaginatedFeatureToggleResponse for requested page criteria , \n<br />  
					   PaginatedFeatureToggleResponse contains   <br />  
	  				  <ul><li>Requested paged  data list  </li>   
	  				  <li>Total number of data available </li>  
	  				  <li>How many items per page   </li></ul>
	  				  """, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedFeatureToggleResponse.class)) }),
			@ApiResponse(responseCode = "204", description = "FeatureToggles not found for requested page criteria", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "API Internal error ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })

	public ResponseEntity<PaginatedFeatureToggleResponse> getPagedFeatureToggles(
			@Parameter(description = "Number of the requested page , <br /> &nbsp;&nbsp; Pages will be starting from 0  , <br /> &nbsp;&nbsp; If the page is -1 or not available ,  One page will return with all available featureToggles   ", required = false) 
			@RequestParam(required = false, defaultValue = "0") int page,
			@Parameter(description = "Number of featureToggles per page , <br /> &nbsp;&nbsp; If number is 0 or not available , <br />  &nbsp;&nbsp;&nbsp;then system default number of results per page <strong> ${app.data.items.per.page} </strong>  will be considered ,   "
					+ "<br /> &nbsp;&nbsp; If requested page (Page number ) is -1  <br /> &nbsp;&nbsp;&nbsp; then this value will be ignored ", required = false) 
			@RequestParam(required = false, defaultValue = "${app.data.items.per.page}") int pageSize)
			throws FeatureToggleException {
		return new ResponseEntity<>(featureToggleService.getFeatureToggles(new PaginatedRequest(page, pageSize)), HttpStatus.OK);

	}
	
	
	/**Update featureToggle in the system 
	 * 
	 * @param featureToggleId
	 * @param featureToggle
	 * @return ResponseEntity<FeatureToggleOut>
	 * 	200 Returns the updated  FeatureToggle
	 *  409 Another featureToggle available with same name in the system 
	 *  404 No featureToggle found for given ID
	 *  500 API Internal error
	 * @throws FeatureToggleException
	 */
	@PatchMapping("/{featureToggleId}")
	@Operation(summary = "Updates a given featureToggle object ", description = "Update featureToggle in the system ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returns the updated FeatureToggle ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureToggleOut.class)) }),
			@ApiResponse(responseCode = "409", description = "Another featureToggle avaiable with same name in the system ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "No featureToggle found for given ID  ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "API Internal error ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<FeatureToggleOut> updateFeatureToggle(
			@Parameter(description = "ID of the featureToggle object ", required = true) @PathVariable long featureToggleId,
			@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "FeatureToggle to update , FeatureToggle name , Should not be empty and should not duplicate", required = true, 
			content = @Content(  schema = @Schema(implementation = FeatureToggleIn.class))) @RequestBody FeatureToggleIn featureToggle)
			throws FeatureToggleException {
 
		featureToggle.setId(featureToggleId);

		return new ResponseEntity<>(featureToggleService.updateFeatureToggle(featureToggle), HttpStatus.OK);
	}

	/**Deletes FeatureToggle objects for given FeatureToggle ID
	 * 
	 * @param featureToggleId
	 * @return ResponseEntity<FeatureToggleOut>
	 *  204 Found and deleted the given object
	 *  404 FeatureToggle not found for given id , Nothing to delete 
	 *  500 API Internal error 
	 * @throws FeatureToggleException
	 */
	@DeleteMapping("/{featureToggleId}")
	@Operation(summary = "Delete featureToggle by id ", description = "Deletes FeatureToggle objects for given FeatureToggle ID ")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Found and deleted the given object"),
			@ApiResponse(responseCode = "404", description = "FeatureToggle not found for given id , Nothing to delete ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "API Internal error ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<FeatureToggleOut> deleteFeatureToggle(
			@Parameter(description = "ID of the featureToggle object ", required = true) @PathVariable long featureToggleId)
			throws FeatureToggleException {
		featureToggleService.deleteFeatureToggles(featureToggleId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
