package model.login.responsewithout_CRN;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSIdeMenu{

	@SerializedName("allshipments")
	private List<AllshipmentsItem> allshipments;

	@SerializedName("status")
	private String status;

	public void setAllshipments(List<AllshipmentsItem> allshipments){
		this.allshipments = allshipments;
	}

	public List<AllshipmentsItem> getAllshipments(){
		return allshipments;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}