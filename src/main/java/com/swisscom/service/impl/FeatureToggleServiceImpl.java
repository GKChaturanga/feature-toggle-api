package com.swisscom.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.swisscom.conf.FeatureToggleException;
import com.swisscom.model.jpa.Customer;
import com.swisscom.model.jpa.FeatureToggle;
import com.swisscom.model.payload.FeatureDisplay;
import com.swisscom.model.payload.FeatureRequest;
import com.swisscom.model.payload.FeatureResponse;
import com.swisscom.model.payload.FeatureToggleIn;
import com.swisscom.model.payload.FeatureToggleOut;
import com.swisscom.model.payload.PaginatedFeatureToggleResponse;
import com.swisscom.model.payload.PaginatedRequest;
import com.swisscom.repository.CustomerRepository;
import com.swisscom.repository.FeatureToggleRepository;
import com.swisscom.service.FeatureToggleService;

@Service
public class FeatureToggleServiceImpl implements FeatureToggleService {
	
	@Value("${app.data.items.per.page}")
	private int pageSize ; 
	
	@Autowired
	FeatureToggleRepository featureToggleRepository ;   
	@Autowired
	private CustomerRepository customerRepository ; 
	
	@Override
	public FeatureToggleOut createFeatureToggle(FeatureToggleIn featuretoggle) {
		 Optional<FeatureToggle > exFeatureToggleDN = featureToggleRepository.getByDisplayName(featuretoggle.getDisplayName());
		 Optional<FeatureToggle > exFeatureToggleTN = featureToggleRepository.getByTechnicalName(featuretoggle.getTechnicalName());
			
		 if(exFeatureToggleDN.isPresent()) {
			 // Found an FeatureToggle with same name  
			 // Throwing new conflict exception 
			 
				 throw new FeatureToggleException
				 .Builder()
				 	.key( FeatureToggleException.FT_NAME_CONFLICT 	)
				 	.status(HttpStatus.CONFLICT)
				 .build();
			 
				 
			 
			 
		 }
		 if(exFeatureToggleTN.isPresent()) {
			 // Found an FeatureToggle with same name  
			 // Throwing new conflict exception 
			 
				 throw new FeatureToggleException
				 .Builder()
				 	.key( 
				 							FeatureToggleException.FT_TECH_NAME_CONFLICT
				 						)
				 	.status(HttpStatus.CONFLICT)
				 .build();
			 
				 
			 
			 
		 }
			 // Crating the FeatureToggle 
			 FeatureToggle createdFt = featureToggleRepository.save( 
						new FeatureToggle.Builder()
							.description(featuretoggle.getDescription())
							.displayName(featuretoggle.getDisplayName())
							.expiresOn(featuretoggle.getExpiresOn())
							.inverted(featuretoggle.isInverted())
							.technicalName(featuretoggle.getTechnicalName()).build()) ;
			 
			 // Build and return FeatureToggleout 
			 
				return  new FeatureToggleOut.Builder()
						.createWith(createdFt)
						.build();
						
						 
		 
	}


	@Override
	public FeatureToggleOut getFeatureToggle(long featureToggleId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PaginatedFeatureToggleResponse getFeatureToggles(PaginatedRequest request) {
		int finalItemsPerPage = request.getResultsPerPage() > 0 ? request.getResultsPerPage() :  pageSize;
		Pageable pageable =
				//If CurrentPage is -1 then take all results in to one page / no pages 
				request.getCurrentPage() == -1 ? Pageable.unpaged():
		PageRequest.of( (int)request.getCurrentPage()  ,
						//If items perpage is requested by client , overide configuration 
						finalItemsPerPage  );
		Page< FeatureToggle> 
		
			listPagedFeatureToggle = featureToggleRepository.findAll(
					pageable );
		
		
		//Converting JPA domains to Response POJOs
		List<FeatureToggleOut> displayList =  listPagedFeatureToggle.toList().stream().map( s->  
		new FeatureToggleOut.Builder()
				.createWith(s)
				.build()
				
	  ).toList() ;

		
		PaginatedFeatureToggleResponse featureToggles =  
				new PaginatedFeatureToggleResponse.Builder()
					.perPage(request.getCurrentPage() == -1  ? displayList.size()  :  finalItemsPerPage)
					.currentPage(request.getCurrentPage())
					.totalCount(listPagedFeatureToggle.getTotalElements())
					.totalPages(listPagedFeatureToggle.getTotalPages())
					
					.payload(displayList ).build();
		
		// If No results for requested page criteria then returns an error  
		if(featureToggles.getPayload().isEmpty()) {
			  throw new FeatureToggleException
			 .Builder()
			 	.key(FeatureToggleException.FT_NOT_AVAIL_PAGE)
			 	.status(HttpStatus.NO_CONTENT)
			 .build(); 
		}
		return featureToggles ; 
		 
	}


	@Override
	public FeatureToggleOut updateFeatureToggle(@Valid FeatureToggleIn featureToggle) {

		 Optional<FeatureToggle> exFeatureToggle = featureToggleRepository.findById(featureToggle.getId());
		 // Find if featureToggle is available  for given ID 
		 if(exFeatureToggle.isPresent()) {
			 Optional<FeatureToggle > exFeatureToggleDN = featureToggleRepository.getByDisplayNameAndIdNot(featureToggle.getDisplayName(),exFeatureToggle.get().getId());
			 Optional<FeatureToggle > exFeatureToggleTN = featureToggleRepository.getByTechnicalNameAndIdNot(featureToggle.getTechnicalName(),exFeatureToggle.get().getId());
				
			 if(exFeatureToggleDN.isPresent()) {
				 // Found an FeatureToggle with same name  
				 // Throwing new conflict exception 
				 
					 throw new FeatureToggleException
					 .Builder()
					 	.key( FeatureToggleException.FT_NAME_CONFLICT 	)
					 	.status(HttpStatus.CONFLICT)
					 .build();
				 
					 
				 
				 
			 }else  if(exFeatureToggleTN.isPresent()) {
				 // Found an FeatureToggle with same name  
				 // Throwing new conflict exception 
				 
					 throw new FeatureToggleException
					 .Builder()
					 	.key( 
					 							FeatureToggleException.FT_TECH_NAME_CONFLICT
					 						)
					 	.status(HttpStatus.CONFLICT)
					 .build();
				 
					 
				 
				 
			 } 
				 
				 // Updating the featureToggle 
				 FeatureToggle updatedFeatureToggle =  featureToggleRepository.save( 
							new  FeatureToggle.Builder()
							.updateWith(exFeatureToggle.get())
							.description(featureToggle.getDescription())
							.displayName(featureToggle.getDisplayName())
							.expiresOn(featureToggle.getExpiresOn())
							.inverted(featureToggle.isInverted())
							.technicalName(featureToggle.getTechnicalName()).build()) ;
				 
				return  new FeatureToggleOut.Builder()
					.createWith(updatedFeatureToggle)
					.build();
					 
			 
			  
			 // No FeatureToggle found for given id 
			 //Throwing an NOT_FOUND Error 
		 }else {
			 throw new FeatureToggleException
			 .Builder()
			 	.key(FeatureToggleException.FT_NOT_FOUND)
			 	.status(HttpStatus.NOT_FOUND)
			 .build();
		 }
		 
	}


	@Override
	public void deleteFeatureToggles(long featureToggleId) {
		
		
	}


	@Override
	public FeatureResponse findFeatureRequests(FeatureRequest featureRequest) {
		Date today = new Date();
		 List<FeatureDisplay> fds = null ; 
		 Optional<Customer> customer =  customerRepository.findById(Long.valueOf(featureRequest.getCustomerId()));
		 if(customer.isPresent()) {
			 List<FeatureToggle> featureToggles = featureToggleRepository.getByTechnicalNameIn(featureRequest.getFeatures());
			 List<FeatureToggle> cusToggles = featureToggleRepository.findFeatureToggleByCustomer(customer.get().getId());
			 
			 fds = featureToggles.stream().map( fd -> {
				
				return new FeatureDisplay(fd.getId(),fd.getDisplayName(), fd.isStatus(), fd.isInverted(),today.compareTo(fd.getExpiresOn()) >= 0, cusToggles.contains(fd),fd.getTechnicalName());
			}).toList();
		 }else {
			 fds = new ArrayList<>();
		 }

		 
		return  new FeatureResponse( fds );
	}

}
