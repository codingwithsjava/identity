package com.example.registerapp.entity;


import org.springframework.data.annotation.Transient;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

  private String email;
  private String password;
 

public boolean isPresent() {
	// TODO Auto-generated method stub
	return true;
}
}
