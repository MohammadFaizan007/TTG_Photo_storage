package model.login.detailsWithoutCrn;

import com.google.gson.annotations.SerializedName;

public class ResponseShipmentDetails{

	@SerializedName("allshipments")
	private Allshipments allshipments;

	@SerializedName("status")
	private String status;

	public void setAllshipments(Allshipments allshipments){
		this.allshipments = allshipments;
	}

	public Allshipments getAllshipments(){
		return allshipments;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}