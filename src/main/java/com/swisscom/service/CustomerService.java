package com.swisscom.service;

import java.util.List;

import com.swisscom.model.payload.CustomerOut;
import com.swisscom.model.payload.FeatureDisplay;
import com.swisscom.model.payload.FetureAttachRequest;

public interface CustomerService {

	List<CustomerOut> listCustomers();

	FeatureDisplay attach(FetureAttachRequest featureRequest);

	FeatureDisplay detach(FetureAttachRequest featureRequest);

}
