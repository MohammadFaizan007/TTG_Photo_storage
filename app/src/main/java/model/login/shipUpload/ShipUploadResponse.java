package model.login.shipUpload;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ShipUploadResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("files_accepted")
	private List<FilesAcceptedItem> filesAccepted;

	@SerializedName("supervisor_sign")
	private String supervisorSign;

	@SerializedName("crn")
	private String crn;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setFilesAccepted(List<FilesAcceptedItem> filesAccepted){
		this.filesAccepted = filesAccepted;
	}

	public List<FilesAcceptedItem> getFilesAccepted(){
		return filesAccepted;
	}

	public void setSupervisorSign(String supervisorSign){
		this.supervisorSign = supervisorSign;
	}

	public String getSupervisorSign(){
		return supervisorSign;
	}

	public void setCrn(String crn){
		this.crn = crn;
	}

	public String getCrn(){
		return crn;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}