package com.register.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.register.dto.LoginDto;
import com.register.entity.RefreshToken;
import com.register.entity.Register;
import com.register.entity.User1;

@FeignClient(name="REGISTER-SERVICE")
public interface ForgotService {
	
	 @PostMapping("/products/loggin")
	 public ResponseEntity login(@RequestBody LoginDto loginDto);

//	 @RequestMapping(value="products/user/{id}",method=RequestMethod.GET)
//	  public User1 getById(@PathVariable String id);
	 
	
}
