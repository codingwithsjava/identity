package com.register.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.register.dto.LoginDto;
import com.register.dto.RegisterDto;
import com.register.entity.User1;
import com.register.exception.UserNotFoundexception;
import com.register.filter.EmailUtil;
import com.register.filter.OtpUtil;
import com.register.repository.UserRepository;
import com.register.response.APIResponse;

import jakarta.mail.MessagingException;

@Service
public class UserService   {
	
	@Autowired
	private OtpUtil otpUtil;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired	
	private ForgotService forgotService;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
//	 public APIResponse register(RegisterDto registerDto) {
//		  //  String otp = otpUtil.generateOtp();
//		    if (userRepository.existsByEmail(registerDto.getEmail())) {
//		        apiResponse.setSuccess(200);
//		        apiResponse.setData("email already exists");
//		        apiResponse.setStatus(false);
//
//		        return apiResponse;
//		    }else {
//		    
//		    User user = new User();
//		    user.setName(registerDto.getName());
//		    user.setEmail(registerDto.getEmail());
//		    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//		  //  user.setOtp(otp);
//		    user.setOtpGeneratedTime(LocalDateTime.now());
//		    userRepository.save(user);
//		 	 apiResponse =new APIResponse(true,200, user, null);
//
//		    //Unable to send otp please try again
//		    return apiResponse;
//		    }
//		    //User registration successful
//		  }
//	
	APIResponse apiResponse=new APIResponse();
	  public APIResponse login(LoginDto loginDto) {
		    User1 user = userRepository.findByEmail(loginDto.getEmail()) .orElseThrow(   () -> new UserNotFoundexception("User not found with this email: " + loginDto.getEmail()));
		    if (!userRepository.existsByEmail(loginDto.getEmail())&&!userRepository.existsByPassword(loginDto.getPassword())) {
		    	apiResponse =new APIResponse(false,200, "email and password is wrong", null);

	            return apiResponse;
	        }
		    if (!BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
		    	apiResponse =new APIResponse(false,200, "Password is incorrect", null);
		      return apiResponse;
		    } 
		    apiResponse =new APIResponse(true,200, "Login successful", null);
		    return apiResponse;
		  }
	
	public ResponseEntity<User1>create(@RequestBody LoginDto loginDto){
		ResponseEntity log=forgotService.login(loginDto);
		User1 user=new User1();
	
		return log;
	}
	
//	public APIResponse get(String id) {
//		User1 user=userRepository.findById(id).orElseThrow(()->new RuntimeException("token not found"));
//		user.setTokens(forgotService.getById(id));
//	    apiResponse =new APIResponse(true,200, user, null);
//
//	//	ResponseEntity token1=forgotService.getById(id);
//		return apiResponse;
//		
//	}


	



}
