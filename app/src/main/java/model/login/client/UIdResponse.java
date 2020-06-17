package model.login.client;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UIdResponse{

	@SerializedName("files")
	private List<FilesItem> files;

	@SerializedName("type")
	private String type;

	@SerializedName("status")
	private String status;

	public List<FilesItem> getFiles(){
		return files;
	}

	public String getType(){
		return type;
	}

	public String getStatus(){
		return status;
	}
}