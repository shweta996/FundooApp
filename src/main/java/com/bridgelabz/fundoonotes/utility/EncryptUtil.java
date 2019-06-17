package com.bridgelabz.fundoonotes.utility;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.dto.loginDto;
import com.bridgelabz.fundoonotes.model.User;

@Component
public class EncryptUtil 
{
	
	public String encryptPassword(String password)
	{
		return new BCryptPasswordEncoder().encode(password);
		
	}
	
	public boolean isPassword(loginDto login, User user)
	{
		return new BCryptPasswordEncoder().matches(login.getPassword(),user.getPassword());
	}
}
