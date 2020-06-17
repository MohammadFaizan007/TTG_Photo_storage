package model.login.checkAssetID;

import com.google.gson.annotations.SerializedName;

public class AssetIDResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("exists")
	private String exists;

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

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}