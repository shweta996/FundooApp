package com.bridgelabz.fundoonotes.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoonotes.model.User;

import java.lang.String;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);
	Optional<User> findById(String id);
	
	

	
}
