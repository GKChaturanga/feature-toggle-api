package com.swisscom.model.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**FeatureToggle Entity 
 * 
 * @author kasunc
 *
 */
@Entity
@Table(name = "customerfeaturetoggle")
public class CustomerFeatureToggle extends SuperEntity{
	
	 
		
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "featuretoggleid", referencedColumnName = "id")
	FeatureToggle featureToggle ;

	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerid", referencedColumnName = "id")
	Customer customer ;

 
	 

 

	public FeatureToggle getFeatureToggle() {
		return featureToggle;
	}


	public void setFeatureToggle(FeatureToggle featureToggle) {
		this.featureToggle = featureToggle;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

 
	
	

}
