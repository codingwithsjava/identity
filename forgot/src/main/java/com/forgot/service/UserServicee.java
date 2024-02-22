package com.forgot.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.forgot.dto.RegisterDto;
import com.forgot.entity.User;
import com.forgot.exception.UnableToSendOtpException;
import com.forgot.exception.UserNotFoundexception;
import com.forgot.repository.UserRepository;
import com.forgot.response.APIResponse;
import com.forgot.util.EmailUtil;
import com.forgot.util.OtpUtil;

import jakarta.mail.MessagingException;

@Service
public class UserServicee {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OtpUtil otpUtil;
	
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private JwtService jwtService;
	
	
	APIResponse apiResponse=new APIResponse();
	
	  public APIResponse forgotpassword(RegisterDto registerDto) {
		    String otp = otpUtil.generateOtp();

			User user=userRepository.findByEmail(registerDto.getEmail()).orElseThrow(
					()->new UserNotFoundexception("User not found with this email"+registerDto.getEmail()));
			try {
				emailUtil.sendOtpEmail(registerDto.getEmail(),otp);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				throw new UnableToSendOtpException("unable to send set password email please try again");
			}
		    user.setOtpGeneratedTime(LocalDateTime.now());
		    user.setOtp(otp);
		    userRepository.save(user);
			apiResponse =new APIResponse(true,200, "Please check your email to set new password", null);
			return apiResponse;
		}

	  public APIResponse verifyAccount(RegisterDto registerDto) {
		    User user = userRepository.findByEmail(registerDto.getEmail())
		    		
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
	  public APIResponse  setPassword(RegisterDto registerDto) {
			User user=userRepository.findByEmail(registerDto.getEmail()).orElseThrow(
					()->new UserNotFoundexception("User not found with this email"+registerDto.getEmail()));
			 if (user.getOtp().equals(registerDto.getOtp()) && Duration.between(user.getOtpGeneratedTime(),
				        LocalDateTime.now()).getSeconds() < (1 * 60)) {
				 
				    //  user.setActive(true);
				 user.setPassword(registerDto.getPassword());
				      userRepository.save(user);
				      apiResponse =new APIResponse(true,200, "your new password set successfully ", null);
				      return apiResponse;
				    }
			// user.setOtpGeneratedTime(LocalDateTime.now());
			// userRepository.save(user);
			apiResponse =new APIResponse(false,200, "please try again", null);
			
			return apiResponse;
		}


}
