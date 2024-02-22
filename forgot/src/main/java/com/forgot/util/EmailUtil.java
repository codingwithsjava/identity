package com.forgot.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

  @Autowired
  private JavaMailSender javaMailSender;

  public void sendOtpEmail(String email, String otp) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
    mimeMessageHelper.setTo(email);
    mimeMessageHelper.setSubject("Verify OTP");
    mimeMessageHelper.setText("OTP is "+otp.formatted(email, otp), true);

    //http://localhost:8085/verify-account?email=sr7363781@gmail.com&otp=527732
    javaMailSender.send(mimeMessage);
  }
  
  public void sendSetPasswordEmail(String email) throws MessagingException {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	    mimeMessageHelper.setTo(email);
	    mimeMessageHelper.setSubject("set password");
	    mimeMessageHelper.setText("""
	        <div>
	          <a href="http://localhost:8085/set-password?email=%s" target="_blank">click link to setpassword</a>
	        </div>
	        """.formatted(email), true);

	    javaMailSender.send(mimeMessage);
	  }
}
