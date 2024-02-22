package com.register.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Logconfig {
	 @Bean
	    public JavaMailSender javaMailSender() {
	        return new JavaMailSenderImpl();
	    }
	 
	 @Bean
	 public BCryptPasswordEncoder passwordEncoder() {
	     return new BCryptPasswordEncoder();
	 }

}
