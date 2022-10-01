package com.swisscom.model.payload;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {
	private List<ValidationError> errors = new ArrayList<>();

	public List<ValidationError> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}
	
	
}