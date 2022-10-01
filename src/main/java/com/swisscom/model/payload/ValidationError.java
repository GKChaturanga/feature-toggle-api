package com.swisscom.model.payload;

public class ValidationError {
	
	public static final class Builder{
		private ValidationError error = new ValidationError();
		public Builder field(String field) {
			this.error.setField(field);
			return this ; 
		}
		public Builder message(String message) {
			this.error.setMessage(message);
			return this ; 
		}
		public Builder value(String value) {
			this.error.setValue(value);
			return this ; 
		}
		
		public ValidationError buidl() {
			
			return this.error ; 
		}
		
		
	}
	
	
	private String field;
	private String message;
	private String value;
	
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}