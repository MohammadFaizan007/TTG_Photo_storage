package model.login.shipUpload.updateShip;

import com.google.gson.annotations.SerializedName;

public class FilesAcceptedItem{

	@SerializedName("desc3")
	private String desc3;

	@SerializedName("file3")
	private String file3;

	@SerializedName("desc2")
	private String desc2;

	@SerializedName("file2")
	private String file2;

	@SerializedName("desc1")
	private String desc1;

	@SerializedName("file1")
	private String file1;

	public String getDesc3(){
		return desc3;
	}

	public String getFile3(){
		return file3;
	}

	public String getDesc2(){
		return desc2;
	}

	public String getFile2(){
		return file2;
	}

	public String getDesc1(){
		return desc1;
	}

	public String getFile1(){
		return file1;
	}
}