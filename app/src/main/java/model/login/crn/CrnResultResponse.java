package model.login.crn;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CrnResultResponse{

	@SerializedName("uids")
	private List<UidsItem> uids;

	@SerializedName("type")
	private String type;

	@SerializedName("status")
	private String status;

	public void setUids(List<UidsItem> uids){
		this.uids = uids;
	}

	public List<UidsItem> getUids(){
		return uids;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}