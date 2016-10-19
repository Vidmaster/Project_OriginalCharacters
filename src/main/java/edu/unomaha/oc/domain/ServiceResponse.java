package edu.unomaha.oc.domain;

public class ServiceResponse {
	private boolean success;
	private String message;
	
	public String getMessage() {
		return message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public ServiceResponse(String message, boolean success) {
		this.success = success;
		this.message = message;
	}
	
	
	
}
