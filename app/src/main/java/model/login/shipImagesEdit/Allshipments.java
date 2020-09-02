package model.login.shipImagesEdit;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Allshipments{

	@SerializedName("date")
	private String date;

	@SerializedName("note")
	private String note;

	@SerializedName("input_time")
	private String inputTime;

	@SerializedName("ship_time")
	private String shipTime;

	@SerializedName("vahicle_number")
	private String vahicleNumber;

	@SerializedName("description")
	private String description;

	@SerializedName("supervisor_name")
	private String supervisorName;

	@SerializedName("logistic_waybill")
	private String logisticWaybill;

	@SerializedName("userid")
	private String userid;

	@SerializedName("box_seal")
	private String boxSeal;

	@SerializedName("no_of_box")
	private String noOfBox;

	@SerializedName("supervisor_photo")
	private String supervisorPhoto;

	@SerializedName("vahicle_type")
	private String vahicleType;

	@SerializedName("supervisor_ph_no")
	private String supervisorPhNo;

	@SerializedName("id")
	private String id;

	@SerializedName("crn")
	private String crn;

	@SerializedName("ship_time_formatted")
	private String shipTimeFormatted;

	@SerializedName("no_of_devices")
	private String noOfDevices;

	@SerializedName("is_reject")
	private String isReject;

	@SerializedName("box_condition")
	private String boxCondition;

	@SerializedName("declr_tick")
	private String declrTick;

	@SerializedName("no_of_pallets")
	private String noOfPallets;

	@SerializedName("no_of_vahicle")
	private String noOfVahicle;

	@SerializedName("pdfurl")
	private String pdfurl;

	@SerializedName("no_of_staff")
	private String noOfStaff;

	@SerializedName("org_ship_time")
	private String orgShipTime;

	@SerializedName("vahicle_container")
	private String vahicleContainer;

	@SerializedName("files")
	private List<FilesItem> files;

	@SerializedName("supervisor_sign")
	private String supervisorSign;

	@SerializedName("ship_date_formatted")
	private String shipDateFormatted;

	@SerializedName("time")
	private String time;

	@SerializedName("device")
	private String device;

	@SerializedName("hash")
	private String hash;

	@SerializedName("logistic_company")
	private String logisticCompany;

	public String getDate(){
		return date;
	}

	public String getNote(){
		return note;
	}

	public String getInputTime(){
		return inputTime;
	}

	public String getShipTime(){
		return shipTime;
	}

	public String getVahicleNumber(){
		return vahicleNumber;
	}

	public String getDescription(){
		return description;
	}

	public String getSupervisorName(){
		return supervisorName;
	}

	public String getLogisticWaybill(){
		return logisticWaybill;
	}

	public String getUserid(){
		return userid;
	}

	public String getBoxSeal(){
		return boxSeal;
	}

	public String getNoOfBox(){
		return noOfBox;
	}

	public String getSupervisorPhoto(){
		return supervisorPhoto;
	}

	public String getVahicleType(){
		return vahicleType;
	}

	public String getSupervisorPhNo(){
		return supervisorPhNo;
	}

	public String getId(){
		return id;
	}

	public String getCrn(){
		return crn;
	}

	public String getShipTimeFormatted(){
		return shipTimeFormatted;
	}

	public String getNoOfDevices(){
		return noOfDevices;
	}

	public String getIsReject(){
		return isReject;
	}

	public String getBoxCondition(){
		return boxCondition;
	}

	public String getDeclrTick(){
		return declrTick;
	}

	public String getNoOfPallets(){
		return noOfPallets;
	}

	public String getNoOfVahicle(){
		return noOfVahicle;
	}

	public String getPdfurl(){
		return pdfurl;
	}

	public String getNoOfStaff(){
		return noOfStaff;
	}

	public String getOrgShipTime(){
		return orgShipTime;
	}

	public String getVahicleContainer(){
		return vahicleContainer;
	}

	public List<FilesItem> getFiles(){
		return files;
	}

	public String getSupervisorSign(){
		return supervisorSign;
	}

	public String getShipDateFormatted(){
		return shipDateFormatted;
	}

	public String getTime(){
		return time;
	}

	public String getDevice(){
		return device;
	}

	public String getHash(){
		return hash;
	}

	public String getLogisticCompany(){
		return logisticCompany;
	}
}