package com.swisscom.conf;

import org.springframework.http.HttpStatus;

public class FeatureToggleException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	public static final String FT_TECH_NAME_CONFLICT = "ft.tech.name.conflict";
	public static final String FT_NAME_CONFLICT = "ft.name.conflict";
	public static final String MESSAGE_SYSTEM_ERROR = "ft.sys.error";
	public static final String FT_NOT_AVAIL_PAGE = "ft.page.not.avaialble";
	public static final String FT_NOT_FOUND = "ft.not.found"; 
	public static final String FT_ALREADY_ATTACHED = "ft.already.attached"; 
	public static final String FT_NOT_ATTACHED = "ft.not.attached"; 
			

	/**
	 * Exception builder 
	 */
	public static final class Builder {

		private String mesageKey;
		private HttpStatus httpStatus;
		private Exception oroginal;

		public Builder key(String key) {

			this.mesageKey = (key);
			return this;
		}

		public Builder status(HttpStatus noContent) {
			this.httpStatus = (noContent);
			return this;
		}

		public Builder original(Exception e) {
			this.oroginal = (e);
			return this;
		}

		public FeatureToggleException build() {
			FeatureToggleException exception = new FeatureToggleException(this.mesageKey);
			exception.setHttpStatus(httpStatus);
			exception.setMesageKey(mesageKey);
			exception.setOroginal(oroginal);

			return exception;
		}

	}

	private String mesageKey;
	private HttpStatus httpStatus;
	private Exception oroginal;

	public FeatureToggleException(String mesageKey) {
		super(mesageKey);
		this.mesageKey = mesageKey;
	}

	public String getMesageKey() {
		return mesageKey;
	}

	public void setMesageKey(String mesageKey) {

		this.mesageKey = mesageKey;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus noContent) {
		this.httpStatus = noContent;
	}

	public Exception getOroginal() {
		return oroginal;
	}

	public void setOroginal(Exception oroginal) {
		this.oroginal = oroginal;
	}
}
