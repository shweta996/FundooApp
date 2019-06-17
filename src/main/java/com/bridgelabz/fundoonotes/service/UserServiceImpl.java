package com.bridgelabz.fundoonotes.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.bridgelabz.fundoonotes.dto.ForgotDto;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.loginDto;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.Email;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.utility.EncryptUtil;
import com.bridgelabz.fundoonotes.utility.JWTTokenGenerator;
import com.bridgelabz.fundoonotes.utility.MailUtil;
import com.bridgelabz.fundoonotes.utility.TokenGenerator;

/**
 * @author:-shweta kale.
 * 
 *
 */

@SuppressWarnings("unused")

@Service("UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private MailUtil mailSender;

	@Autowired
	private EncryptUtil encryptUtil;
	
	

	/**
	 *details:-for register user.
	 */
	@Override
	public Response onRegister(UserDto userdto, StringBuffer requestUrl) {
		Optional<User> isEmail = userRepository.findByEmail(userdto.getEmail());
		User user = modelMapper.map(userdto, User.class);
		if (!isEmail.isPresent()) {

			user.setPassword(encoder.encode(userdto.getPassword()));
			User saveUser = userRepository.save(user);

			try {

				System.out.println(user.getId());
				String token = tokenGenerator.generateToken(saveUser.getId());
				System.out.println("token:" + token);
				String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/verification/" + token;
				Email email = new Email();
				email.setTo(saveUser.getEmail());
				email.setSubject("Email verified");
				email.setBody("please verify your email by using activation URL:" + activationUrl);
				mailSender.send(email);
				return new Response(200, "registration done and email send to user successfully", null);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new UserException("something went wrong");
			}
		} else {
			throw new UserException("Existing user");
		}

	}

	/**
	 *details:-for login user.
	 */
	@Override
	public Response onLogin(loginDto logindto) {
		Optional<User> optionalUser = userRepository.findByEmail(logindto.getEmail());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			String id = user.getId();
			try {
				if (encryptUtil.isPassword(logindto, user)) {
					return new Response(200, "Sucessfully logged in", null);
				} else {
					throw new UserException("please enter correct password");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new UserException("Something went wrong");
			}

		} else {
			throw new UserException("Authentication failed");
		}
	}

	/**
	 *details:-to check user is validate or not.
	 */
	@Override
	public Response validateUser(String token) {
		String id = tokenGenerator.VerifyToken(token);

		Optional<User> optional = userRepository.findById(id);
		if (optional.isPresent()) {
			User user = optional.get();
			user.setIsverify(true);
			userRepository.save(user);
			return new Response( 200,"verified user",null);

		} else {
			return new Response (-2,"user is not registered",null);
		}

	}

	
	/**
	 *details:-for forgot user.
	 */
	@Override
	public Response forgotPassword(String email, StringBuffer requestUrl) {
		Optional<User> optionalUser=userRepository.findByEmail(email);
		User user=optionalUser.get();
		String id=user.getId();
		if(optionalUser.isPresent())
		{	
			try
			{
				String token=tokenGenerator.generateToken(id);
				System.out.println("token:"+token);
				String resetUrl=requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/forget/" + token;
				Email sendEmail=new Email();
				sendEmail.setTo(user.getEmail());
				sendEmail.setSubject("resetPassword");
				sendEmail.setBody("please reset your password by using following link:"+resetUrl);
				mailSender.send(sendEmail);
				return new Response(200,"link send to your mail successfully",null);
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new UserException("Internal server error");
				
			}
		}
		else
		{
			throw new UserException("user not present");
		}
		
	}

	/**
	 *details:-for reset password.
	 */
	@Override
	public Response resetPassword(String password, String token) {
		String id=tokenGenerator.VerifyToken(token);
		System.out.println("user id:"+id);
		Optional<User> optinalUser=userRepository.findById(id);
		if(optinalUser.isPresent())
		{
		  User user= optinalUser.get();
		  user.setPassword(encoder.encode(password));
		  userRepository.save(user);
		  return new Response(200,"password changed successfully",null);
		}
		else
		{
			return new Response(-2,"password not changed",null);
		}
		
		
	}

}
