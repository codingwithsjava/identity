package com.register.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.register.response.APIResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundexception.class)
	public ResponseEntity<APIResponse> handleUserNotFoundException(UserNotFoundexception ex){
		APIResponse apiResponse =new APIResponse(false,200,ex.getMessage(),null);
		return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}

	

}
