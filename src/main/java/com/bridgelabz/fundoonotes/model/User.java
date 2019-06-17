package com.bridgelabz.fundoonotes.model;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User 
{
	@Id
	private String id;
	@NotNull
	@NotEmpty(message = "name should be not empty")
	private String name;
	private String email;
	private String password;
	private long phone_number;
	
	@NotNull
	private String address;
	private boolean isverify;

	public User() {
	}

	public User(String id, String name, String email, String password, long phone_number, String address,
			boolean isverify) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone_number = phone_number;
		this.address = address;
		this.isverify = isverify;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(long phone_number) {
		this.phone_number = phone_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isIsverify() {
		return isverify;
	}

	public void setIsverify(boolean isverify) {
		this.isverify = isverify;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", phone_number="
				+ phone_number + ", address=" + address + ", isverify=" + isverify + "]";
	}

	
	

}
