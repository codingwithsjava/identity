package com.example.registerapp.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EmailConfig {
  @Value("${spring.mail.host}")
  private String mailHost;
  @Value("${spring.mail.port}")
  private String mailPort;
  @Value("${spring.mail.username}")
  private String mailUsername;
  @Value("${spring.mail.password}")
  private String mailPassword;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost(mailHost);
    javaMailSender.setPort(Integer.parseInt(mailPort));
    javaMailSender.setUsername(mailUsername);
    javaMailSender.setPassword(mailPassword);

    Properties props = javaMailSender.getJavaMailProperties();
    props.put("mail.smtp.starttls.enable", "true");
    return javaMailSender;
  }
//  @Bean
//	public PasswordEncoder passwordEncoder() {
//   	return new BCryptPasswordEncoder();
//   }

}
