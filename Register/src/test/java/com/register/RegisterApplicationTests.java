package com.register;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.register.entity.User;
import com.register.service.ForgotService;

@SpringBootTest
class RegisterApplicationTests {

	@Test
	void contextLoads() {
	}
	
    @Autowired
    private ForgotService ratingService;

  

}
