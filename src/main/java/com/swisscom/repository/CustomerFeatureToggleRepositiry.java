package com.swisscom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swisscom.model.jpa.CustomerFeatureToggle;

public interface CustomerFeatureToggleRepositiry extends JpaRepository<CustomerFeatureToggle, Long> {
	@Query("SELECT cf FROM CustomerFeatureToggle cf WHERE cf.customer.id = :cusId and cf.featureToggle.id =  :ftId ")
	Optional<CustomerFeatureToggle> findFeatureToggleByCustomerAndFeture(@Param("cusId") long cusId,@Param("ftId") long ftId );
}
