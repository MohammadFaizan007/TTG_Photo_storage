package model.login.detailsWithoutCrn;

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

	@SerializedName("userid")
	private String userid;

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

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setNote(String note){
		this.note = note;
	}

	public String getNote(){
		return note;
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

	public void setVahicleNumber(String vahicleNumber){
		this.vahicleNumber = vahicleNumber;
	}

	public String getVahicleNumber(){
		return vahicleNumber;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setSupervisorName(String supervisorName){
		this.supervisorName = supervisorName;
	}

	public String getSupervisorName(){
		return supervisorName;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	public void setNoOfBox(String noOfBox){
		this.noOfBox = noOfBox;
	}

	public String getNoOfBox(){
		return noOfBox;
	}

	public void setSupervisorPhoto(String supervisorPhoto){
		this.supervisorPhoto = supervisorPhoto;
	}

	public String getSupervisorPhoto(){
		return supervisorPhoto;
	}

	public void setVahicleType(String vahicleType){
		this.vahicleType = vahicleType;
	}

	public String getVahicleType(){
		return vahicleType;
	}

	public void setSupervisorPhNo(String supervisorPhNo){
		this.supervisorPhNo = supervisorPhNo;
	}

	public String getSupervisorPhNo(){
		return supervisorPhNo;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCrn(String crn){
		this.crn = crn;
	}

	public String getCrn(){
		return crn;
	}

	public void setShipTimeFormatted(String shipTimeFormatted){
		this.shipTimeFormatted = shipTimeFormatted;
	}

	public String getShipTimeFormatted(){
		return shipTimeFormatted;
	}

	public void setNoOfDevices(String noOfDevices){
		this.noOfDevices = noOfDevices;
	}

	public String getNoOfDevices(){
		return noOfDevices;
	}

	public void setIsReject(String isReject){
		this.isReject = isReject;
	}

	public String getIsReject(){
		return isReject;
	}

	public void setBoxCondition(String boxCondition){
		this.boxCondition = boxCondition;
	}

	public String getBoxCondition(){
		return boxCondition;
	}

	public void setDeclrTick(String declrTick){
		this.declrTick = declrTick;
	}

	public String getDeclrTick(){
		return declrTick;
	}

	public void setNoOfPallets(String noOfPallets){
		this.noOfPallets = noOfPallets;
	}

	public String getNoOfPallets(){
		return noOfPallets;
	}

	public void setNoOfVahicle(String noOfVahicle){
		this.noOfVahicle = noOfVahicle;
	}

	public String getNoOfVahicle(){
		return noOfVahicle;
	}

	public void setPdfurl(String pdfurl){
		this.pdfurl = pdfurl;
	}

	public String getPdfurl(){
		return pdfurl;
	}

	public void setNoOfStaff(String noOfStaff){
		this.noOfStaff = noOfStaff;
	}

	public String getNoOfStaff(){
		return noOfStaff;
	}

	public void setVahicleContainer(String vahicleContainer){
		this.vahicleContainer = vahicleContainer;
	}

	public String getVahicleContainer(){
		return vahicleContainer;
	}

	public void setFiles(List<FilesItem> files){
		this.files = files;
	}

	public List<FilesItem> getFiles(){
		return files;
	}

	public void setSupervisorSign(String supervisorSign){
		this.supervisorSign = supervisorSign;
	}

	public String getSupervisorSign(){
		return supervisorSign;
	}

	public void setShipDateFormatted(String shipDateFormatted){
		this.shipDateFormatted = shipDateFormatted;
	}

	public String getShipDateFormatted(){
		return shipDateFormatted;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setDevice(String device){
		this.device = device;
	}

	public String getDevice(){
		return device;
	}

	public void setHash(String hash){
		this.hash = hash;
	}

	public String getHash(){
		return hash;
	}

	public void setLogisticCompany(String logisticCompany){
		this.logisticCompany = logisticCompany;
	}

	public String getLogisticCompany(){
		return logisticCompany;
	}
}