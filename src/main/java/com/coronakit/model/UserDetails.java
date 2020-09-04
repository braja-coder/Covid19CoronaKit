package com.coronakit.model;

import java.io.Serializable;

public class UserDetails implements Serializable{
	
	private String userName;
	private String email;
	private String phone;
	
	public UserDetails(String userName, String email, String phone) {
		super();
		this.userName = userName;
		this.email = email;
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
