package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.loginDto;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;

import javax.servlet.http.*;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Response>registerUser(@RequestBody UserDto userdto,HttpServletRequest request)
	{
		StringBuffer headerUrl=request.getRequestURL();
		Response response=userService.onRegister(userdto, headerUrl);
		
		return new ResponseEntity<Response>(response,HttpStatus.OK);		
			
	}
	
	@RequestMapping("/verification/{token}")
	public ResponseEntity<Response> emailValidate(@PathVariable String token)
	{
		Response response=userService.validateUser(token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
   @GetMapping("/login")
   public ResponseEntity<Response>loginUser(@RequestBody loginDto logindto,HttpServletRequest request)
	{
		Response response = userService.onLogin(logindto);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
   
   @GetMapping("/forget")
   public ResponseEntity<Response>forgetPassword(@RequestParam String email,HttpServletRequest request)
   {
	   StringBuffer requestUrl=request.getRequestURL();
		Response response=userService.forgotPassword(email, requestUrl);
		
		return new ResponseEntity<Response>(response,HttpStatus.OK);	
   }
   
   @PutMapping("/reset")
   public ResponseEntity<Response>resetPassword(@RequestParam String password,@RequestParam String token)
   {
	   Response response=userService.resetPassword(password, token);
	   return new ResponseEntity<Response>(response,HttpStatus.OK);
	   
   }
   
   
}

