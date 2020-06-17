package com.ttg_photo_storage.retrofit;
import model.login.LoginResponse;
import model.login.changepassword.ChangePasswordResponse;
import model.login.checkAssetID.AssetIDResponse;
import model.login.client.UIdResponse;
import model.login.crn.CrnResultResponse;
import model.login.upload.UploadPhotoResponse;
import model.login.viewProfile.ViewProfileResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {
    @FormUrlEncoded
    @POST("api.php/")
    Call<LoginResponse> login(
            @Field("action") String action,
            @Field("username") String username,
            @Field("pass") String password,
            @Field("device") String device

    );

    @FormUrlEncoded
    @POST("api.php/")
    Call<UIdResponse> post(
            @Field("action") String action,
            @Field("uid") String uid,
            @Field("token") String token,
            @Field("ver") Integer ver
    );



    @FormUrlEncoded
    @POST("api.php/")
    Call<CrnResultResponse> crnResult(
            @Field("action") String action,
            @Field("token") String token,
            @Field("crn") String crn

    );

    @FormUrlEncoded
    @POST("api.php/")
    Call<AssetIDResponse> checkID(
            @Field("action") String action,
            @Field("token") String token,
            @Field("uid") String uid

    );

    @FormUrlEncoded
    @POST("api.php/")
    Call<ViewProfileResponse> profile(
            @Field("action") String action,
            @Field("token") String token

    );

    @FormUrlEncoded
    @POST("api.php/")
    Call<ChangePasswordResponse> change(
            @Field("action") String action,
            @Field("token") String token,
            @Field("oldpass") String oldpass,
            @Field("pass")String pass
    );

    @Multipart
    @POST("api.php/")
    Call<UploadPhotoResponse> UploadFile6(
            @Part("token") RequestBody token,
            @Part("action") RequestBody action,
            @Part("description") RequestBody description,
            @Part() MultipartBody.Part file1,
            @Part() MultipartBody.Part file2,
            @Part() MultipartBody.Part file3,
            @Part() MultipartBody.Part file4,
            @Part() MultipartBody.Part file5,
            @Part() MultipartBody.Part file6,
            @Part("desc1") RequestBody desc1,
            @Part("desc2") RequestBody desc2,
            @Part("desc3") RequestBody desc3,
            @Part("desc4") RequestBody desc4,
            @Part("desc5") RequestBody desc5,
            @Part("desc6") RequestBody desc6,
            @Part("uid") RequestBody uid,
            @Part("crn") RequestBody crn
    );


}
