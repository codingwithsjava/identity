package com.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.register.dto.LoginDto;
import com.register.dto.RegisterDto;
import com.register.entity.User1;
import com.register.response.APIResponse;
import com.register.service.ForgotService;
import com.register.service.UserService;


@RestController
@RequestMapping("/log")
public class LoginController {
	
	@Autowired
	Environment environment;
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private ForgotService forgotService;
	
@Autowired
private RestTemplate restTemplate;
APIResponse apiResponse=new APIResponse();
	
@PostMapping("/login")
public ResponseEntity login(@RequestBody LoginDto loginDto) {
	System.out.println(environment.getProperty("local.server.port"));
  return new ResponseEntity(userService.login(loginDto), HttpStatus.OK);
}

@GetMapping("/get")
public String getOne() {
    return "this is login ";
}



//@PostMapping("/register")
//public ResponseEntity register(@RequestBody RegisterDto registerDto) {
//  return new ResponseEntity(userService.register(registerDto), HttpStatus.OK);
//}

}
