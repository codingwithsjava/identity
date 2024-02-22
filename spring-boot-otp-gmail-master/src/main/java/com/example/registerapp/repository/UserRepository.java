package com.example.registerapp.repository;

import com.example.registerapp.entity.Register;
import com.example.registerapp.entity.UserInfo;
import com.example.registerapp.response.APIResponse;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Register, String> {

  Optional<Register> findByEmail(String email);

  Optional<Register> findBytoken(String token);

  Optional<Register> findById(String id);

  boolean existsByEmail(String email);
Optional<UserInfo> findByName(String username);


APIResponse findAllBytoken(String token);
boolean existsById(String id);

Optional<Register> findByPassword(String password);







}
