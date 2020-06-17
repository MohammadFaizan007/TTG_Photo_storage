package model.login.upload;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UploadPhotoResponse{

	@SerializedName("uid")
	private String uid;

	@SerializedName("files_desc")
	private String filesDesc;

	@SerializedName("files_accepted")
	private List<FilesAcceptedItem> filesAccepted;

	@SerializedName("crn")
	private String crn;

	@SerializedName("status")
	private String status;

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

	public void setFilesDesc(String filesDesc){
		this.filesDesc = filesDesc;
	}

	public String getFilesDesc(){
		return filesDesc;
	}

	public void setFilesAccepted(List<FilesAcceptedItem> filesAccepted){
		this.filesAccepted = filesAccepted;
	}

	public List<FilesAcceptedItem> getFilesAccepted(){
		return filesAccepted;
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