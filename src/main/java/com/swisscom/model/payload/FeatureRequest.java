package com.swisscom.model.payload;

import java.util.List;

public class FeatureRequest {
	private String customerId ;
	private List<String> features ;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public List<String> getFeatures() {
		return features;
	}
	public void setFeatures(List<String> features) {
		this.features = features;
	} 
	
	
	
}
