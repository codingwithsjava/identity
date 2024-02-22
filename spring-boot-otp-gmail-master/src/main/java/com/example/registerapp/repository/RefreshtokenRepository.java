package com.example.registerapp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.registerapp.entity.RefreshToken;

public interface RefreshtokenRepository  extends MongoRepository<RefreshToken, String>{

	Optional<RefreshToken> findByToken(String token);
	

}
