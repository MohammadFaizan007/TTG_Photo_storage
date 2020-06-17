package model.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("user")
	private User user;

	@SerializedName("status")
	private String status;

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}