package com.parkingProject.ControllerAdvice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.parkingProject.Exception.EmptyHandlerException;

@ControllerAdvice
public class ParkingHandlerException {

	@ExceptionHandler(EmptyHandlerException.class)
	public ResponseEntity<String> handleEmptyException(EmptyHandlerException emptyHandlerException){
		
		return new ResponseEntity<String>("Input Field is Empty !",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException NoSuchElementException){
		
		return new ResponseEntity<String>("No Value is Present In DB, Please Change your Request !",HttpStatus.NOT_FOUND);	
	}
	
}
