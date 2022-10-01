package com.swisscom.repository;

import java.util.List;
import java.util.Optional;
 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swisscom.model.jpa.FeatureToggle;

@Repository

public interface FeatureToggleRepository extends JpaRepository<FeatureToggle, Long> {
	Optional<FeatureToggle> getByTechnicalName(String technicalName);
	Optional<FeatureToggle> getByDisplayName(String displayName);
	
	
	Optional<FeatureToggle> getByTechnicalNameAndIdNot(String technicalName,long id );
	Optional<FeatureToggle> getByDisplayNameAndIdNot(String displayName,long id );
	
	List<FeatureToggle> getByTechnicalNameIn(List<String> technicalNames);
	
	@Query("SELECT cf.featureToggle FROM CustomerFeatureToggle cf WHERE cf.customer.id = :cusId")
	List<FeatureToggle> findFeatureToggleByCustomer(@Param("cusId") long cusId);
}
