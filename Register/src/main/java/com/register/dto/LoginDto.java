package com.register.dto;


import org.springframework.data.annotation.Transient;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

  private String email;
  private String password;
  private String token;
 

public boolean isPresent() {
	// TODO Auto-generated method stub
	return true;
}
}
