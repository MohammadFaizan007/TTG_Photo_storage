package model.login.ViewShip;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ViewShipResponse{

	@SerializedName("shipments")
	private List<ShipmentsItem> shipments;

	@SerializedName("status")
	private String status;

	public List<ShipmentsItem> getShipments(){
		return shipments;
	}

	public String getStatus(){
		return status;
	}
}