package com.swisscom.service;

import java.util.List;

import javax.validation.Valid;

import com.swisscom.model.payload.FeatureDisplay;
import com.swisscom.model.payload.FeatureRequest;
import com.swisscom.model.payload.FeatureToggleIn;
import com.swisscom.model.payload.FeatureToggleOut;
import com.swisscom.model.payload.PaginatedFeatureToggleResponse;
import com.swisscom.model.payload.PaginatedRequest;

public interface FeatureToggleService {

	FeatureToggleOut createFeatureToggle(FeatureToggleIn featuretoggle);

	FeatureToggleOut getFeatureToggle(long featureToggleId);

	PaginatedFeatureToggleResponse getFeatureToggles(PaginatedRequest paginatedRequest);

	FeatureToggleOut updateFeatureToggle(@Valid FeatureToggleIn featureToggle);

	void deleteFeatureToggles(long featureToggleId);

	List<FeatureDisplay> findFeatureRequests(FeatureRequest featureRequest);

}
