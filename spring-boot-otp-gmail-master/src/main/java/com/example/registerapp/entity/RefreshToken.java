package com.example.registerapp.entity;

import java.time.Instant;

import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
	
	@Id
	private String id;
	private String token;
	
	private Instant expiryDate; 
	
	@DBRef
	@Field("user_id")
	private UserInfo userInfo;

}
