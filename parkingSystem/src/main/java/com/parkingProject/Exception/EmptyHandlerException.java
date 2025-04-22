package com.parkingProject.Exception;

public class EmptyHandlerException extends RuntimeException{
	
	String error_message;
	
	String error_code;

	public EmptyHandlerException() {
		// TODO Auto-generated constructor stub
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	@Override
	public String toString() {
		return "EmptyHandlerException [error_message=" + error_message + ", error_code=" + error_code + "]";
	}

	
	
	

}
