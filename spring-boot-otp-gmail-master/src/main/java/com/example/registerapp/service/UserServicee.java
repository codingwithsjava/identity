package com.example.registerapp.service;

import com.example.registerapp.dto.AuthRequest;
import com.example.registerapp.dto.LoginDto;
import com.example.registerapp.dto.RegisterDto;
import com.example.registerapp.entity.Register;
import com.example.registerapp.entity.UserInfo;
import com.example.registerapp.exception.UnableToSendOtpException;
import com.example.registerapp.exception.UserNotFoundexception;
import com.example.registerapp.repository.UserInfoRepository;
import com.example.registerapp.repository.UserRepository;
import com.example.registerapp.response.APIResponse;
import com.example.registerapp.util.EmailUtil;
import com.example.registerapp.util.OtpUtil;

import jakarta.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UserServicee {

  @Autowired
  private OtpUtil otpUtil;
  @Autowired
  private EmailUtil emailUtil;
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private UserInfoRepository userInfoRepository;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Autowired
  private JwtService jwtService;
  
  

  


  APIResponse apiResponse=new APIResponse();
  public APIResponse register(RegisterDto registerDto) {
    String otp = otpUtil.generateOtp();
    if (userRepository.existsByEmail(registerDto.getEmail())) {
        apiResponse.setSuccess(200);
        apiResponse.setData("email already exists");
        apiResponse.setStatus(false);

        return apiResponse;
    }else {
    try {
      emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
  

    } catch (MessagingException e) {
    	throw new UnableToSendOtpException("Unable to send otp please try again");
    }
    Register user = new Register();
    user.setName(registerDto.getName());
    user.setEmail(registerDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setOtp(otp);
    user.setOtpGeneratedTime(LocalDateTime.now());
    userRepository.save(user);
 	 apiResponse =new APIResponse(true,200, user, null);

    //Unable to send otp please try again
    return apiResponse;
    }
    //User registration successful
  }

  public APIResponse verifyAccount(RegisterDto registerDto) {
    Register user = userRepository.findByEmail(registerDto.getEmail())
    		
        .orElseThrow(() -> new UserNotFoundexception("User not found with this email: " + registerDto.getEmail()));
    if (user.getOtp().equals(registerDto.getOtp()) && Duration.between(user.getOtpGeneratedTime(),
        LocalDateTime.now()).getSeconds() < (1 * 60)) {
        String token=jwtService.generateToken(user.getEmail());
        registerDto.setToken(token);
      user.setActive(true);
      user.setOtpGeneratedTime(LocalDateTime.now());
      user.setToken(registerDto.getToken());
      
      userRepository.save(user);
      apiResponse =new APIResponse(true,200, user, null);
      return apiResponse;
    }
    apiResponse =new APIResponse(false,200, "Please regenrate otp and try again", null);
    return apiResponse;
  }
  
  public APIResponse getById(String id) {
 Register user=userRepository.findById(id).orElse(null);
	  APIResponse apiResponse =new APIResponse(true,200,user , null);

      return apiResponse;
  }
  
  public APIResponse getBytoken(String token) {
 Register user=userRepository.findBytoken(token).orElse(null);
	  APIResponse apiResponse =new APIResponse(true,200,user, null);

      return apiResponse;
  }
//  public APIResponse getUserById(String id) {
//	  RegisterDto registerDto=new RegisterDto();
//	    User user = userRepository.findById(registerDto.getId()) .orElseThrow(() -> new UserNotFoundexception("User not found with this email: " + registerDto.getEmail()));
//
//	    	userRepository.findById(id).orElse(null);
//	    		
//		    apiResponse =new APIResponse(true,200, user, null);
//	   
//	    return apiResponse;
//	  }

  public APIResponse regenerateOtp(RegisterDto registerDto) {
    Register user = userRepository.findByEmail(registerDto.getEmail())
        .orElseThrow(() -> new UserNotFoundexception("User not found with this email: " + registerDto.getEmail()));
    String otp = otpUtil.generateOtp();
    try {
      emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
    } catch (MessagingException e) {
      throw new UnableToSendOtpException("Unable to send otp please try again");
    }
    user.setOtp(otp);
    user.setOtpGeneratedTime(LocalDateTime.now());
    userRepository.save(user);
    apiResponse =new APIResponse(true,200, "Email sent... please verify account within 1 minute", null);
    return apiResponse;
  }

  public APIResponse login(LoginDto loginDto) {
    Register user = userRepository.findByEmail(loginDto.getEmail())
        .orElseThrow(
            () -> new UserNotFoundexception("User not found with this email: " + loginDto.getEmail()));
    if (!BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
    	apiResponse =new APIResponse(false,200, "Password is incorrect", null);
      return apiResponse;
    } else if (!user.isActive()) {
    	apiResponse =new APIResponse(false,200, "your account is not verified", null);
      return apiResponse;
    }
    apiResponse =new APIResponse(true,200, "Login successful", null);
    return apiResponse;
  }
//  public APIResponse forgotpassword(RegisterDto registerDto) {
//	    String otp = otpUtil.generateOtp();
//
//		User user=userRepository.findByEmail(registerDto.getEmail()).orElseThrow(
//				()->new UserNotFoundexception("User not found with this email"+registerDto.getEmail()));
//		try {
//			emailUtil.sendOtpEmail(registerDto.getEmail(),otp);
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			throw new UnableToSendOtpException("unable to send set password email please try again");
//		}
//	    user.setOtpGeneratedTime(LocalDateTime.now());
//	    user.setOtp(otp);
//	    userRepository.save(user);
//		apiResponse =new APIResponse(true,200, "Please check your email to set new password", null);
//		return apiResponse;
//	}

//public APIResponse  setPassword(RegisterDto registerDto) {
//	User user=userRepository.findByEmail(registerDto.getEmail()).orElseThrow(
//			()->new UserNotFoundexception("User not found with this email"+registerDto.getEmail()));
//	 if (user.getOtp().equals(registerDto.getOtp()) && Duration.between(user.getOtpGeneratedTime(),
//		        LocalDateTime.now()).getSeconds() < (1 * 60)) {
//		 
//		    //  user.setActive(true);
//		 user.setPassword(registerDto.getPassword());
//		      userRepository.save(user);
//		      apiResponse =new APIResponse(true,200, "your new password set successfully ", null);
//		      return apiResponse;
//		    }
//	// user.setOtpGeneratedTime(LocalDateTime.now());
//	// userRepository.save(user);
//	apiResponse =new APIResponse(false,200, "please try again", null);
//	
//	return apiResponse;
//}

 public String addUser(UserInfo userInfo) {
	        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	        userInfoRepository.save(userInfo);
	        return "user added to system ";
	    }

	public APIResponse get(){
		
		List<Register> list=userRepository.findAll();
		apiResponse=new  APIResponse(true,200,list,null);

		
		return apiResponse;
		
	}

	







}
