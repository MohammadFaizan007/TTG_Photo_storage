package model.login.shipUpload.updateShip;

import com.google.gson.annotations.SerializedName;

public class UpdateShipResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("status")
	private String status;

	public String getMsg(){
		return msg;
	}

	public String getStatus(){
		return status;
	}
}