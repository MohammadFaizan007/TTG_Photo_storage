package model.login.responsewithout_CRN;

import com.google.gson.annotations.SerializedName;

public class FilesItem{

	@SerializedName("file")
	private String file;

	@SerializedName("desc")
	private Object desc;

	public String getFile(){
		return file;
	}

	public Object getDesc(){
		return desc;
	}
}