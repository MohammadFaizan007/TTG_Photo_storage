package model.login.shipUpload.updateShip;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UpdateImagesResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("files_accepted")
	private List<FilesAcceptedItem> filesAccepted;

	@SerializedName("status")
	private String status;

	public String getMsg(){
		return msg;
	}

	public List<FilesAcceptedItem> getFilesAccepted(){
		return filesAccepted;
	}

	public String getStatus(){
		return status;
	}
}