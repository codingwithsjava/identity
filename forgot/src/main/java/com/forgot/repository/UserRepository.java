package com.forgot.repository;



import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.forgot.entity.User;
import com.forgot.response.APIResponse;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByEmail(String email);

  Optional<User> findBytoken(String token);

  Optional<User> findById(String id);

  boolean existsByEmail(String email);
//Optional<UserInfo> findByName(String username);


APIResponse findAllBytoken(String token);
boolean existsById(String id);






}
