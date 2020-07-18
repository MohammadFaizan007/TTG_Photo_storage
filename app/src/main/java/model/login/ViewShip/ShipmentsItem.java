package model.login.ViewShip;

import com.google.gson.annotations.SerializedName;

public class ShipmentsItem{

	@SerializedName("ship_time_formatted")
	private String shipTimeFormatted;

	@SerializedName("input_time")
	private String inputTime;

	@SerializedName("ship_time")
	private String shipTime;

	@SerializedName("is_reject")
	private String isReject;

	@SerializedName("ship_date_formatted")
	private String shipDateFormatted;

	@SerializedName("box_condition")
	private String boxCondition;

	@SerializedName("crn")
	private String crn;

	@SerializedName("hash")
	private String hash;

	public String getShipTimeFormatted(){
		return shipTimeFormatted;
	}

	public String getInputTime(){
		return inputTime;
	}

	public String getShipTime(){
		return shipTime;
	}

	public String getIsReject(){
		return isReject;
	}

	public String getShipDateFormatted(){
		return shipDateFormatted;
	}

	public String getBoxCondition(){
		return boxCondition;
	}

	public String getCrn(){
		return crn;
	}

	public String getHash(){
		return hash;
	}
}