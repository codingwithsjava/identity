//package com.register.serviceImpl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.register.dto.LoginDto;
//import com.register.entity.User;
//import com.register.repository.UserRepository;
//import com.register.response.APIResponse;
//import com.register.service.ForgotService;
//import com.register.service.UserService;
//
//public class UserServiceImpl implements UserService {
//	
//	@Autowired
//	private UserRepository userRepository;
//	
////	@Autowired
////	private ForgotService forgotService;
////	APIResponse apiResponse=new APIResponse();
////	
////	  public APIResponse login(LoginDto loginDto) {
////	        	
////	           // User user = restTemplate.getForObject("http://localhost:8085/products/loggin" , User.class);
////		 LoginDto user=forgotService.login(loginDto);
////	              apiResponse =new APIResponse(true,200, user, null);
////
//////	              if(email.isEmpty()){
//////	            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//////	        }else{
//////	            return new ResponseEntity<>("No Student Found",HttpStatus.NOT_FOUND);
//////	        }
////	   return apiResponse;
////	  }	
//
//}
