package com.swisscom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swisscom.model.jpa.Customer;
import com.swisscom.model.jpa.FeatureToggle;

@Repository

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}
