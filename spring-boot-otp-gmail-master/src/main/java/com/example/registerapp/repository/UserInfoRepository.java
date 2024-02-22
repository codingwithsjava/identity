package com.example.registerapp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.registerapp.entity.UserInfo;


public interface UserInfoRepository extends MongoRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);

}