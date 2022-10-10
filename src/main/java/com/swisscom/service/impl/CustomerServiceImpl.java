package com.swisscom.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.swisscom.conf.FeatureToggleException;
import com.swisscom.model.jpa.CustomerFeatureToggle;
import com.swisscom.model.jpa.FeatureToggle;
import com.swisscom.model.payload.CustomerOut;
import com.swisscom.model.payload.FeatureDisplay;
import com.swisscom.model.payload.FetureAttachRequest;
import com.swisscom.repository.CustomerFeatureToggleRepositiry;
import com.swisscom.repository.CustomerRepository;
import com.swisscom.repository.FeatureToggleRepository;
import com.swisscom.service.CustomerService;



@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository ; 
	
	@Autowired
	private FeatureToggleRepository featureToggleRepository ; 
	
	@Autowired
	private CustomerFeatureToggleRepositiry customerFeatureToggleRepositiry;
	
	@Override
	public List<CustomerOut> listCustomers() {
		return customerRepository.findAll().stream().map( c -> new CustomerOut(c.getId(), c.getName())).toList();
	}

	@Override 
	public FeatureDisplay attach(FetureAttachRequest featureRequest) {
	
		Optional<CustomerFeatureToggle> cusFetures = customerFeatureToggleRepositiry.findFeatureToggleByCustomerAndFeture(Long.valueOf(featureRequest.customerId()), Long.valueOf(featureRequest.featureId()));
		if(cusFetures.isEmpty()) {
			CustomerFeatureToggle customerFeatureToggle = new CustomerFeatureToggle();
			FeatureToggle fetureToggle = featureToggleRepository.findById(Long.valueOf(featureRequest.featureId())).orElseThrow();
			customerFeatureToggle.setFeatureToggle(fetureToggle);
			customerFeatureToggle.setCustomer(customerRepository.findById(Long.valueOf(featureRequest.customerId())).orElseThrow());
			
			Date today = new Date();
			customerFeatureToggle.setCreatedDate(today);
			customerFeatureToggle.setLastUpdated(today);
			customerFeatureToggleRepositiry.save(customerFeatureToggle);
			
			return new FeatureDisplay(fetureToggle.getId(),fetureToggle.getDisplayName(), fetureToggle.isStatus(), fetureToggle.isInverted(),today.compareTo(fetureToggle.getExpiresOn()) >= 0, true,fetureToggle.getTechnicalName());
		}else {
			 throw new FeatureToggleException
			 .Builder()
			 	.key(FeatureToggleException.FT_ALREADY_ATTACHED)
			 	.status(HttpStatus.NOT_FOUND)
			 .build(); 
		}
		  
		
	}

	@Override
	public FeatureDisplay detach(FetureAttachRequest featureRequest) {
		Optional<CustomerFeatureToggle> cusFetures = customerFeatureToggleRepositiry.findFeatureToggleByCustomerAndFeture(Long.valueOf(featureRequest.customerId()), Long.valueOf(featureRequest.featureId()));
		if(cusFetures.isPresent() ) {
			CustomerFeatureToggle customerFeatureToggle = cusFetures.get();
			 
			 
			customerFeatureToggleRepositiry.delete(customerFeatureToggle);
			
			return new FeatureDisplay();
		}else {
			 throw new FeatureToggleException
			 .Builder()
			 	.key(FeatureToggleException.FT_NOT_ATTACHED)
			 	.status(HttpStatus.NOT_FOUND)
			 .build(); 
		}
	}

}
