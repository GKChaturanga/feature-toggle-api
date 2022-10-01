package com.swisscom.conf;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.swisscom.model.payload.ErrorResponse;
import com.swisscom.model.payload.ValidationError;
import com.swisscom.model.payload.ValidationErrorResponse;
 
/**
 * FeatureToggleExceptionHandler used to handle exceptions on the 
 *  operation  and send a meaning full repose to client 
 * 
 * @author kasunc
 *
 */
@ControllerAdvice
public class FeatureToggleExceptionHandler extends ResponseEntityExceptionHandler {
 
	@Autowired
	MessageSource messageSource;

	@ExceptionHandler(value = { FeatureToggleException.class })
	protected ResponseEntity<Object> handleFeatureToggleExceptions(FeatureToggleException ex, WebRequest request) {
		//Define the error message according to FeatureToggleException key 
		return handleExceptionInternal(ex,
				new ErrorResponse(messageSource.getMessage(ex.getMesageKey(), null, Locale.getDefault()).toString()), new HttpHeaders(),
				ex.getHttpStatus(), request);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult result = ex.getBindingResult();
		ValidationErrorResponse resp = new ValidationErrorResponse();
		
		 
		result.getFieldErrors().forEach(
				fieldError ->  
					resp.getErrors().add(
							new ValidationError.Builder()
							.field(fieldError.getField() )
							.message(fieldError.getDefaultMessage())
							.value(fieldError.getRejectedValue()==null?"null":fieldError.getRejectedValue().toString()).buidl()
					) 
		);

		return handleExceptionInternal(ex, resp, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	

}