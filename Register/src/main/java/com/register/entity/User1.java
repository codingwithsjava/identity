package com.register.entity;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.ResponseEntity;

import com.register.dto.LoginDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "User")
public class User1 {

  private String id;
 private String name;
  private String email;
  private String password;
  private boolean active;
  private String otp;
  private LocalDateTime otpGeneratedTime;
  private String token;
  transient private User1 tokens;
 // transient LoginDto loginDto;

  
  
}
