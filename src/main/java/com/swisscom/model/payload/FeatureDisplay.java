package com.swisscom.model.payload;

public record FeatureDisplay(long id , String name , boolean active , boolean inverted , boolean expired , boolean customerFound,String technicalName) {

 
	 public FeatureDisplay( ) { 
		 	this(0, "", false, false, false, false, "");
	    }
	
	
	
	
	
}
