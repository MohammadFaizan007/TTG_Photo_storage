package model.login.crn;

import com.google.gson.annotations.SerializedName;

public class UidsItem{

	@SerializedName("uid")
	private String uid;

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}
}