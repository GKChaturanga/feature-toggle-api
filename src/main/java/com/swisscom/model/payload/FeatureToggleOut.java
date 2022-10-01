package com.swisscom.model.payload;

import java.util.Date;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swisscom.model.jpa.FeatureToggle;
import com.swisscom.model.jpa.FeatureToggle.Builder;

public class FeatureToggleOut {
	/**
	 * FeatureToggle builder for update and create 
	 * */
	
	@Value("${app.date.format.with.time}")
	private static String DATE_FORMAT ; 
	public static final class Builder {
		
		
		private FeatureToggleOut featureToggle ;
		private long id ; 
		private String displayName;
		private String technicalName;
		private Date expiresOn;
		private String description;
		private boolean inverted;
		
		
		public Builder displayName(String displayName) {
			this.displayName = displayName ; 
			return this ; 
		}
		public Builder id(long id) {
			this.id = id ; 
			return this ; 
		}
		public Builder technicalName(String technicalName) {
			this.technicalName = technicalName ; 
			return this ; 
		}
		public Builder expiresOn(Date expiresOn) {
			 

			this.expiresOn = expiresOn ; 
			return this ; 
		}
		
		public Builder description(String description) {
			this.description = description ; 
			return this ; 
		}
		
		public Builder inverted(boolean inverted) {
			this.inverted = inverted ; 
			return this ; 
		}
		public Builder createWith(FeatureToggle featureToggle) {
			this. id = featureToggle.getId() ; 
			this. description = featureToggle.getDescription() ;  
			this. displayName = featureToggle.getDisplayName() ;
			this. expiresOn= featureToggle.getExpiresOn() ; 
			this. inverted = featureToggle.isInverted() ; 
			this. technicalName  = featureToggle.getTechnicalName();
			return this ; 
		}
		 
		
		public FeatureToggleOut build() {
			 
				this.featureToggle = new FeatureToggleOut();
			 
			 
			this.featureToggle .id = this.id ; 
			this.featureToggle.description = this.description ;  
			this.featureToggle.displayName = this.displayName ;
			this.featureToggle.expiresOn= this.expiresOn ; 
			this.featureToggle.inverted = this.inverted ; 
			this.featureToggle.technicalName  = this.technicalName;
			
			
			return featureToggle ;
		}
		

		
	}
	 
	
	 
	private long id ; 
	
	 
	private String displayName;
	
	 
	private String technicalName;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")

	private Date expiresOn;
	
 
	private String description;
	 
	private boolean inverted;

	public long getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getTechnicalName() {
		return technicalName;
	}

	public Date getExpiresOn() {
		return expiresOn;
	}

	public String getDescription() {
		return description;
	}

	public boolean isInverted() {
		return inverted;
	}

	@Override
	public String toString() {
		return "FeatureToggleOut [id=" + id + ", displayName=" + displayName + ", technicalName=" + technicalName
				+ ", expiresOn=" + expiresOn + ", description=" + description + ", inverted=" + inverted + "]";
	}
	
	 

 
 


}
