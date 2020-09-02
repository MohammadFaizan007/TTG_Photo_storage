package model.login.shipImagesEdit;

import com.google.gson.annotations.SerializedName;

public class ShipImagesResponse{

	@SerializedName("allshipments")
	private Allshipments allshipments;

	@SerializedName("status")
	private String status;

	public Allshipments getAllshipments(){
		return allshipments;
	}

	public String getStatus(){
		return status;
	}
}