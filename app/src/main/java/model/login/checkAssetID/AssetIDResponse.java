package model.login.checkAssetID;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AssetIDResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("exists")
	private String exists;

	@SerializedName("files")
	private List<FilesItem> files;

	@SerializedName("status")
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setExists(String exists){
		this.exists = exists;
	}

	public String getExists(){
		return exists;
	}

	public void setFiles(List<FilesItem> files){
		this.files = files;
	}

	public List<FilesItem> getFiles(){
		return files;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}