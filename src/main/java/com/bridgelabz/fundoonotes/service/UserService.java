package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.ForgotDto;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.loginDto;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.response.Response;

@SuppressWarnings("unused")
public interface UserService {

	Response onRegister(UserDto userdto,StringBuffer requestUrl);

	Response onLogin(loginDto logindto);
	
	Response validateUser(String token);
	
	Response forgotPassword(String email,StringBuffer requestUrl);
	
	Response resetPassword(String password,String token);
	
	
}
