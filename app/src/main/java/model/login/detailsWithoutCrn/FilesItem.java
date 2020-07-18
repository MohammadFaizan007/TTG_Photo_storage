package model.login.detailsWithoutCrn;

import com.google.gson.annotations.SerializedName;

public class FilesItem{

	@SerializedName("file")
	private String file;

	@SerializedName("desc")
	private String desc;

	public void setFile(String file){
		this.file = file;
	}

	public String getFile(){
		return file;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}
}