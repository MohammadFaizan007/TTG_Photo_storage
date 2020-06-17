package model.login.viewProfile;

import com.google.gson.annotations.SerializedName;

public class ViewProfileResponse{

	@SerializedName("current_user")
	private CurrentUser currentUser;

	@SerializedName("status")
	private String status;

	public CurrentUser getCurrentUser(){
		return currentUser;
	}

	public String getStatus(){
		return status;
	}
}