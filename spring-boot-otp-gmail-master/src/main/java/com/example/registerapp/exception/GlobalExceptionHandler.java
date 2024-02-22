package com.example.registerapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.registerapp.response.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundexception.class)
	public ResponseEntity<APIResponse> handleUserNotFoundException(UserNotFoundexception ex){
		APIResponse apiResponse =new APIResponse(false,200,ex.getMessage(),null);
		return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(TokenNotFoundException.class)
	public ProblemDetail handleTokenNotFoundException(TokenNotFoundException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
	@ExceptionHandler(UnableToSendOtpException.class)
public ResponseEntity<APIResponse> handleUnableTosendOtpException(UnableToSendOtpException ex){
		APIResponse apiResponse=new  APIResponse(false,200,ex.getMessage(),null);
		return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	

}
