package com.swisscom.model.jpa;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;
/**FeatureToggle Entity 
 * 
 * @author kasunc
 *
 */
@Entity
@Table(name = "featuretoggle")
public class FeatureToggle extends SuperEntity{
	
	
	private static String DATE_FORMAT  = "dd/MM/yyyy HH:mm:ss";	
	/**
	 * FeatureToggle builder for update and create 
	 * */
	public static final class Builder {
		
 
		
		private FeatureToggle featureToggle ;
 
		private String displayName;
		private String technicalName;
		private Date expiresOn;
		private String description;
		private boolean inverted;
		boolean status  = true ;
		
		
		public Builder displayName(String displayName) {
			this.displayName = displayName ; 
			return this ; 
		}
		public Builder technicalName(String technicalName) {
			this.technicalName = technicalName ; 
			return this ; 
		}
		public Builder expiresOn(String expiresOn) {
			 

			try {
				System.out.println("DATE_FORMAT => " + DATE_FORMAT);
				this.expiresOn = new SimpleDateFormat(DATE_FORMAT).parse( expiresOn ) ;
			} catch (ParseException e) {
				 
				e.printStackTrace();
			} 
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
		 
		public Builder updateWith(FeatureToggle featureToggle) {
			this.featureToggle = featureToggle ; 
			return this ; 
		}
		
		public FeatureToggle build() {
			if(this.featureToggle == null ) {
				this.featureToggle = new FeatureToggle();
				this.featureToggle.setCreatedDate(new Date());
			}
			this.featureToggle.setLastUpdated(new Date());
			this.featureToggle.description = this.description ;  
			this.featureToggle.displayName = this.displayName ;
			this.featureToggle.expiresOn= this.expiresOn ; 
			this.featureToggle.inverted = this.inverted ; 
			this.featureToggle.technicalName  = this.technicalName;
			this.featureToggle.status = this.status; 
			
			return featureToggle ;
		}
		

		
	}
	 
	
	
	
	@Column(name = "displayname")
	private String displayName;
	
	@Column(name = "technicalname")
	private String technicalName;
	
	@Column(name = "expireson")
	private Date expiresOn;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "inverted")
	private boolean inverted;
	@Column(name = "status")
	boolean status ;
	
	public FeatureToggle() { }
	
	 

	public FeatureToggle(String displayName, String technicalName, Date expiresOn, String description,
			boolean inverted) {
		super();
		this.displayName = displayName;
		this.technicalName = technicalName;
		this.expiresOn = expiresOn;
		this.description = description;
		this.inverted = inverted;
	}


	 

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


	public Date getExpiresOn() {
		return expiresOn;
	}


	public void setExpiresOn(Date expiresOn) {
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



	@Override
	public String toString() {
		return "FeatureToggle [id=" +  getId() + ", displayName=" + displayName + ", technicalName=" + technicalName
				+ ", expiresOn=" + expiresOn + ", description=" + description + ", inverted=" + inverted + ", status="
				+ status + "]";
	}



	public boolean isStatus() {
		return status;
	}



	public void setStatus(boolean status) {
		this.status = status;
	}


 

	 
	
	

}
