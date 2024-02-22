package com.register.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.register.dto.LoginDto;
import com.register.entity.User1;


@Repository
public interface UserRepository extends MongoRepository<User1, String> {
	  Optional<User1> findByEmail(String email);

	//  Optional<User> findBytoken(String token);

	 // Optional<User1> findById(String id);

	boolean existsByEmail(String email);

	boolean existsByPassword(String password);

}
