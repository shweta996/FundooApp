package com.bridgelabz.fundoonotes.utility;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenGenerator implements TokenGenerator{
	
	 String secret_token="shk29969999";

	public String generateToken(String id) {
	
		String token=Jwts.builder()
					.setSubject("fundoo")
					.setExpiration(new Date(System.currentTimeMillis()+1000*1000))
					.setId(id)
					.signWith(SignatureAlgorithm.HS256, secret_token)
					.compact();
		return token;
					
	}

	@Override
	public String VerifyToken(String token) {
	
		Jws<Claims> claims = Jwts.parser()
		  .setSigningKey(secret_token)
		  .parseClaimsJws(token);
		String id = claims.getBody().getId();
		return id;
		
	}

}
