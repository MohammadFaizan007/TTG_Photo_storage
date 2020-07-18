package model.login.responsewithout_CRN;

import com.google.gson.annotations.SerializedName;

public class AllshipmentsItem{

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

	public void setShipTimeFormatted(String shipTimeFormatted){
		this.shipTimeFormatted = shipTimeFormatted;
	}

	public String getShipTimeFormatted(){
		return shipTimeFormatted;
	}

	public void setInputTime(String inputTime){
		this.inputTime = inputTime;
	}

	public String getInputTime(){
		return inputTime;
	}

	public void setShipTime(String shipTime){
		this.shipTime = shipTime;
	}

	public String getShipTime(){
		return shipTime;
	}

	public void setIsReject(String isReject){
		this.isReject = isReject;
	}

	public String getIsReject(){
		return isReject;
	}

	public void setShipDateFormatted(String shipDateFormatted){
		this.shipDateFormatted = shipDateFormatted;
	}

	public String getShipDateFormatted(){
		return shipDateFormatted;
	}

	public void setBoxCondition(String boxCondition){
		this.boxCondition = boxCondition;
	}

	public String getBoxCondition(){
		return boxCondition;
	}

	public void setCrn(String crn){
		this.crn = crn;
	}

	public String getCrn(){
		return crn;
	}

	public void setHash(String hash){
		this.hash = hash;
	}

	public String getHash(){
		return hash;
	}
}