package com.bridgelabz.fundoonotes.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.model.Email;

@Component
public class MailUtil{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void send(Email email)
	{
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getBody());
		
		javaMailSender.send(message);
		System.out.println("email send successfully..!!");
	}
	
}
