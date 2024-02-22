package com.example.registerapp.controller;


import com.example.registerapp.dto.AuthRequest;
import com.example.registerapp.dto.JwtResponse;
import com.example.registerapp.dto.LoginDto;
import com.example.registerapp.dto.RefreshTokenRequest;
import com.example.registerapp.dto.RegisterDto;
import com.example.registerapp.entity.RefreshToken;
import com.example.registerapp.entity.Register;
import com.example.registerapp.entity.UserInfo;
import com.example.registerapp.repository.UserRepository;
import com.example.registerapp.response.APIResponse;
import com.example.registerapp.service.JwtService;
import com.example.registerapp.service.RefreshTokenService;
import com.example.registerapp.service.UserServicee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class UserControler {

  @Autowired
  private UserServicee userService;
  @Autowired
  private AuthenticationManager authenticationManager;
  
  @Autowired
  private RefreshTokenService refreshTokenService;
  
  
  @Autowired
  private JwtService jwtService;
  
  
  @Autowired
  private UserRepository userRepository;
  
	APIResponse apiResponse=new APIResponse();

  
  @PostMapping("/register")
  public ResponseEntity register(@RequestBody RegisterDto registerDto) {
    return new ResponseEntity(userService.register(registerDto), HttpStatus.OK);
  }

  @PostMapping("/verify-account")
  public ResponseEntity verifyAccount(@RequestBody RegisterDto registerDto) {
    return new ResponseEntity(userService.verifyAccount(registerDto), HttpStatus.OK);
  }

  @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
  public ResponseEntity<List<Register>> getById(@PathVariable String id) {
    Optional<Register> user = userRepository.findById(id);

    if (user.isPresent()) {
      return new ResponseEntity(userService.getById(id), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  
  @PostMapping("/")
  public ResponseEntity getBytokenId(@RequestHeader String token) {
    Optional<Register> user = userRepository.findBytoken(token);

    if (user.isPresent()) {
      return new ResponseEntity(userService.getBytoken(token), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  @PostMapping("/regenerate-otp")
  public ResponseEntity regenerateOtp(@RequestBody RegisterDto registerDto) {
    return new ResponseEntity(userService.regenerateOtp(registerDto), HttpStatus.OK);
  }
//  @PostMapping("/loggin")
//  public ResponseEntity login(@RequestBody LoginDto loginDto) {
//    return new ResponseEntity(userService.login(loginDto), HttpStatus.OK);
//  }
  
//  @PostMapping("/forgot-password")
//	public ResponseEntity forgotpassword(@RequestBody RegisterDto registerDto){
//		return new ResponseEntity(userService.forgotpassword(registerDto),HttpStatus.OK);
//	}
  
//  @PostMapping("/set-password")
//  public ResponseEntity setPassword(@RequestBody RegisterDto registerDto){
//	  return new ResponseEntity(userService.setPassword(registerDto),HttpStatus.OK);
//  }
  
  @PostMapping("/new")	
  public String addNewUser(@RequestBody UserInfo userInfo) {
      return userService.addUser(userInfo);
  }
  @GetMapping("/all")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
public ResponseEntity get() {
	    return new ResponseEntity(userService.get(), HttpStatus.OK);
	  }
  @PostMapping("/authenticate")
  public APIResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
      if (authentication.isAuthenticated()) {
       //   return jwtService.generateToken(authRequest.getUsername());
    	RefreshToken  refreshToken=  refreshTokenService.createRefeshToken(authRequest.getUsername());
    	//jwtService.generateToken(authRequest.getUsername());
    	JwtResponse.builder().accessToken(jwtService.generateToken(authRequest.getUsername()))
    	.token(refreshToken.getToken()).build();
    	apiResponse.setData(refreshToken);
  		apiResponse=new  APIResponse(true,200,jwtService.generateToken(authRequest.getUsername()),null);

		    return apiResponse;

      } else {
          throw new UsernameNotFoundException("invalid user request !");
      }


  }
  

//  @PostMapping("/user")
//  public ResponseEntity getuser(@RequestParam String email,@RequestParam String password) {
//    Optional<Register> user = userRepository.findByEmail(email);
//    Optional<Register> user1 = userRepository.findByPassword(password);
//
//
//    if (user.isPresent()) {
//      return new ResponseEntity(userService.getUser(email, password), HttpStatus.OK);
//    } else {
//      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//  }

  @PostMapping("/refreshToken")
  public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
	  return  refreshTokenService.findByToken(refreshTokenRequest.getToken())
	  .map(refreshTokenService::verifyExpiration)
	  .map(RefreshToken::getUserInfo)
	  .map(userInfo->{
		  String accessToken=jwtService.generateToken(userInfo.getName());
		  return JwtResponse.builder()
				.accessToken(accessToken)
				.token(refreshTokenRequest.getToken())
				.build();
	  }).orElseThrow(()->new  RuntimeException("FRefresh token is not in database"));
	  
	  
  }

}
