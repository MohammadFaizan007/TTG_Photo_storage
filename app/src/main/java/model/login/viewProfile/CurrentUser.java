package model.login.viewProfile;

import com.google.gson.annotations.SerializedName;

public class CurrentUser{

	@SerializedName("country")
	private String country;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("type")
	private String type;

	@SerializedName("email")
	private String email;

	public String getCountry(){
		return country;
	}

	public String getName(){
		return name;
	}

	public String getMobile(){
		return mobile;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getType(){
		return type;
	}

	public String getEmail(){
		return email;
	}
}