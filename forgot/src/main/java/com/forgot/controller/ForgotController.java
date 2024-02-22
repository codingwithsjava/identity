package com.forgot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forgot.dto.RegisterDto;
import com.forgot.service.UserServicee;


@RestController
@RequestMapping("/forgot")
public class ForgotController {
	
	@Autowired
	private UserServicee userServicee;
	

	
	
	 @PostMapping("/forgot-password")
		public ResponseEntity forgotpassword(@RequestBody RegisterDto registerDto){
			return new ResponseEntity(userServicee.forgotpassword(registerDto),HttpStatus.OK);
		}
	 
	  @PostMapping("/verify-account")
	  public ResponseEntity verifyAccount(@RequestBody RegisterDto registerDto) {
	    return new ResponseEntity(userServicee.verifyAccount(registerDto), HttpStatus.OK);
	  }
	  @PostMapping("/set-password")
	  public ResponseEntity setPassword(@RequestBody RegisterDto registerDto){
		  return new ResponseEntity(userServicee.setPassword(registerDto),HttpStatus.OK);
	  }
	  

}
