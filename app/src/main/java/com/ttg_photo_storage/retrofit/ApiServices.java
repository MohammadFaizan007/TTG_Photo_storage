package com.ttg_photo_storage.retrofit;

import model.login.LoginResponse;
import model.login.ViewShip.ViewShipResponse;
import model.login.changepassword.ChangePasswordResponse;
import model.login.checkAssetID.AssetIDResponse;
import model.login.client.UIdResponse;
import model.login.crn.CrnResultResponse;
import model.login.detailsWithoutCrn.ResponseShipmentDetails;
import model.login.editProfile.EditProfileResponse;
import model.login.responsewithout_CRN.ResponseSIdeMenu;
import model.login.shipUpload.ShipUploadResponse;
import model.login.upload.UploadPhotoResponse;
import model.login.viewProfile.ViewProfileResponse;
import model.login.viewShipDetails.ViewShipDetailsResponse;
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
            @Field("pass") String pass
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
            @Part() MultipartBody.Part file7,
            @Part() MultipartBody.Part file8,
            @Part() MultipartBody.Part file9,
            @Part() MultipartBody.Part file10,
            @Part() MultipartBody.Part file11,
            @Part() MultipartBody.Part file12,
            @Part("desc1") RequestBody desc1,
            @Part("desc2") RequestBody desc2,
            @Part("desc3") RequestBody desc3,
            @Part("desc4") RequestBody desc4,
            @Part("desc5") RequestBody desc5,
            @Part("desc6") RequestBody desc6,
            @Part("desc7") RequestBody desc7,
            @Part("desc8") RequestBody desc8,
            @Part("desc9") RequestBody desc9,
            @Part("desc10") RequestBody desc10,
            @Part("desc11") RequestBody desc11,
            @Part("desc12") RequestBody desc12,
            @Part("uid") RequestBody uid,
            @Part("crn") RequestBody crn
    );

    @Multipart
    @POST("api.php/")
    Call<ShipUploadResponse> ShipUpload(
            @Part("token") RequestBody token,
            @Part("action") RequestBody action,
            @Part("crn") RequestBody crn,
            @Part("description") RequestBody description,
            @Part() MultipartBody.Part file1,
            @Part() MultipartBody.Part file2,
            @Part() MultipartBody.Part file3,
            @Part() MultipartBody.Part file4,
            @Part() MultipartBody.Part file5,
            @Part() MultipartBody.Part file6,
            @Part() MultipartBody.Part file7,
            @Part() MultipartBody.Part file8,
            @Part() MultipartBody.Part file9,
            @Part() MultipartBody.Part file10,
            @Part() MultipartBody.Part file11,
            @Part() MultipartBody.Part file12,
            @Part() MultipartBody.Part file13,
            @Part() MultipartBody.Part file14,
            @Part() MultipartBody.Part file15,
            @Part("desc1") RequestBody desc1,
            @Part("desc2") RequestBody desc2,
            @Part("desc3") RequestBody desc3,
            @Part("desc4") RequestBody desc4,
            @Part("desc5") RequestBody desc5,
            @Part("desc6") RequestBody desc6,
            @Part("desc7") RequestBody desc7,
            @Part("desc8") RequestBody desc8,
            @Part("desc9") RequestBody desc9,
            @Part("desc10") RequestBody desc10,
            @Part("desc11") RequestBody desc11,
            @Part("desc12") RequestBody desc12,
            @Part("desc13") RequestBody desc13,
            @Part("desc14") RequestBody desc14,
            @Part("desc15") RequestBody desc15,
            @Part("input_time") RequestBody input_time,
            @Part("ship_time") RequestBody ship_time,
            @Part("logistic_company") RequestBody logistic_company,
            @Part("vahicle_type") RequestBody vahicle_type,
            @Part("vahicle_container") RequestBody vahicle_container,
            @Part("vahicle_number") RequestBody vahicle_number,
            @Part("box_condition") RequestBody box_condition,
            @Part("supervisor_name") RequestBody supervisor_name,
            @Part() MultipartBody.Part supervisor_sign,
            @Part("note") RequestBody note,
            @Part("no_of_staff") RequestBody no_of_staff,
            @Part("no_of_box") RequestBody no_of_box,
            @Part("no_of_pallets") RequestBody no_of_pallets,
            @Part("no_of_devices") RequestBody no_of_devices,
            @Part("no_of_vahicle") RequestBody no_of_vahicle,
            @Part("supervisor_ph_no") RequestBody supervisor_ph_no,
            @Part("is_reject") RequestBody is_reject
    );


    @Multipart
    @POST("api.php/")
    Call<ShipUploadResponse> ShipUploadResject(
            @Part("token") RequestBody token,
            @Part("action") RequestBody action,
            @Part("crn") RequestBody crn,
            @Part() MultipartBody.Part file1,
            @Part() MultipartBody.Part file2,
            @Part() MultipartBody.Part file3,
            @Part() MultipartBody.Part file4,
            @Part() MultipartBody.Part file5,
            @Part() MultipartBody.Part file6,
            @Part() MultipartBody.Part file7,
            @Part() MultipartBody.Part file8,
            @Part() MultipartBody.Part file9,
            @Part() MultipartBody.Part file10,
            @Part() MultipartBody.Part file11,
            @Part() MultipartBody.Part file12,
            @Part() MultipartBody.Part file13,
            @Part() MultipartBody.Part file14,
            @Part() MultipartBody.Part file15,
            @Part("desc1") RequestBody desc1,
            @Part("desc2") RequestBody desc2,
            @Part("desc3") RequestBody desc3,
            @Part("desc4") RequestBody desc4,
            @Part("desc5") RequestBody desc5,
            @Part("desc6") RequestBody desc6,
            @Part("desc7") RequestBody desc7,
            @Part("desc8") RequestBody desc8,
            @Part("desc9") RequestBody desc9,
            @Part("desc10") RequestBody desc10,
            @Part("desc11") RequestBody desc11,
            @Part("desc12") RequestBody desc12,
            @Part("desc13") RequestBody desc13,
            @Part("desc14") RequestBody desc14,
            @Part("desc15") RequestBody desc15,
            @Part("input_time") RequestBody input_time,
            @Part("ship_time") RequestBody ship_time,
            @Part("logistic_company") RequestBody logistic_company,
            @Part("vahicle_type") RequestBody vahicle_type,
            @Part("vahicle_container") RequestBody vahicle_container,
            @Part("vahicle_number") RequestBody vahicle_number,
            @Part("box_condition") RequestBody box_condition,
            @Part("supervisor_name") RequestBody supervisor_name,
//            @Part() MultipartBody.Part supervisor_sign,
            @Part("note") RequestBody note,
            @Part("no_of_box") RequestBody no_of_box,
            @Part("no_of_pallets") RequestBody no_of_pallets,
            @Part("no_of_devices") RequestBody no_of_devices,
            @Part("no_of_vahicle") RequestBody no_of_vahicle,
            @Part("supervisor_ph_no") RequestBody supervisor_ph_no,
            @Part("is_reject") RequestBody is_reject
    );

    @FormUrlEncoded
    @POST("api.php/")
    Call<ViewShipResponse> viewShip(
            @Field("action") String action,
            @Field("token") String token,
            @Field("crn") String crn,
            @Field("input_time") String input_time

    );

//    @FormUrlEncoded
//    @POST("api.php/")
//    Call<ViewShipResponse> viewShipStaff(
//            @Field("action") String action,
//            @Field("token") String token
//
//    );


    @Multipart
    @POST("api.php/")
    Call<EditProfileResponse> profileEdit(
            @Part("action") RequestBody action,
            @Part("token") RequestBody token,
            @Part("firstname") RequestBody firstname,
            @Part("mobile") RequestBody mobile,
            @Part() MultipartBody.Part profile_pic

    );

    @FormUrlEncoded
    @POST("api.php/")
    Call<ViewShipDetailsResponse> viewShipDetails(
            @Field("action") String action,
            @Field("token") String token,
            @Field("crn") String crn,
            @Field("hash") String hash,
            @Field("input_time") String input_time

    );

    @FormUrlEncoded
    @POST("api.php/")
    Call<ResponseShipmentDetails> viewShipDetailsShip(
            @Field("action") String action,
            @Field("token") String token,
            @Field("hash") String hash,
            @Field("input_time") String input_time

    );

    @FormUrlEncoded
    @POST("api.php/")
    Call<ResponseSIdeMenu> viewShip(
            @Field("action") String action,
            @Field("token") String token,
            @Field("input_time") String input_time

    );


}
