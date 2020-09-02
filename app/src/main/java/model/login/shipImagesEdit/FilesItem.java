package model.login.shipImagesEdit;

import com.google.gson.annotations.SerializedName;

public class FilesItem{

	@SerializedName("file")
	private String file;

	@SerializedName("desc")
	private String desc;

	public String getFile(){
		return file;
	}

	public String getDesc(){
		return desc;
	}
}