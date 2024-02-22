package com.example.registerapp.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.registerapp.entity.RefreshToken;
import com.example.registerapp.repository.RefreshtokenRepository;
import com.example.registerapp.repository.UserInfoRepository;

import jakarta.websocket.server.ServerEndpoint;

@Service
public class RefreshTokenService {

	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private RefreshtokenRepository refreshtokenRepository;
	
	public RefreshToken createRefeshToken(String username) {
		RefreshToken refreshToken=
		RefreshToken.builder()
		.userInfo(userInfoRepository.findByName(username).get())
		.token(UUID.randomUUID().toString())
		.expiryDate(Instant.now().plusMillis(600000))
		.build();
		
		return refreshtokenRepository.save(refreshToken);
	}
	
	public Optional<RefreshToken> findByToken(String token){
		return refreshtokenRepository.findByToken(token);
	}
	
	public  RefreshToken verifyExpiration(RefreshToken token) {
		if(token.getExpiryDate().compareTo(Instant.now())<0) {
			refreshtokenRepository.delete(token);
			throw new RuntimeException(token.getToken()+"Refresh token was expired please make a new sing request");
		}
		return token;
	}

	
	

}
