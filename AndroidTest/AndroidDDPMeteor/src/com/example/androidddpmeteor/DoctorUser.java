package com.example.androidddpmeteor;

public class DoctorUser {
	public String name;
	public String password;
	public String token;
	public String id;
	public String tokenExpires;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTokenExpires() {
		return tokenExpires;
	}
	public void setTokenExpires(String tokenExpires) {
		this.tokenExpires = tokenExpires;
	}
	@Override
	public String toString() {
		return "DoctorUser [name=" + name + ", password=" + password
				+ ", token=" + token + ", id=" + id + ", tokenExpires="
				+ tokenExpires + "]";
	}
	public DoctorUser(String name, String password, String token, String id,
			String tokenExpires) {
		super();
		this.name = name;
		this.password = password;
		this.token = token;
		this.id = id;
		this.tokenExpires = tokenExpires;
	}
	public DoctorUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
