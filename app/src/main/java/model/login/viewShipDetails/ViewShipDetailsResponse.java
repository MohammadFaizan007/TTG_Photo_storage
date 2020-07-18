package model.login.viewShipDetails;

import com.google.gson.annotations.SerializedName;

public class ViewShipDetailsResponse{

	@SerializedName("shipments")
	private Shipments shipments;

	@SerializedName("status")
	private String status;

	public void setShipments(Shipments shipments){
		this.shipments = shipments;
	}

	public Shipments getShipments(){
		return shipments;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}