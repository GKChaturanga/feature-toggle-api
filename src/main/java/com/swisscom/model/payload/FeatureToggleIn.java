package com.swisscom.model.payload;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swisscom.conf.CustomDateConstraint;
 

public class FeatureToggleIn {
	
	@NotEmpty(message = "{ft.name.notempty}")
	private String displayName;
	
	@NotEmpty(message = "{ft.tech.name.notempty}")
	private String technicalName;
	
	@CustomDateConstraint 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private String expiresOn;
	
	private long id ; 
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	private String description;
	
	
	private boolean inverted  = false ;


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public String getTechnicalName() {
		return technicalName;
	}


	public void setTechnicalName(String technicalName) {
		this.technicalName = technicalName;
	}


	public String getExpiresOn() {
		return expiresOn;
	}


	public void setExpiresOn(String expiresOn) {
		this.expiresOn = expiresOn;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isInverted() {
		return inverted;
	}


	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}


	 
	
	
	
	
}
