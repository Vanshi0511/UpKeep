package com.example.upkeep;

import com.example.upkeep.models.AddPaymentModel;
import com.example.upkeep.models.AddPropertyModel;
import com.example.upkeep.models.AddRepairContactModel;
import com.example.upkeep.models.EditProfileModel;
import com.example.upkeep.models.LoginModel;
import com.example.upkeep.models.LoginResponseModel;
import com.example.upkeep.models.RegisterResponseModel;
import com.example.upkeep.models.SendResetModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiSets {

    //for registration
    @Multipart
    @POST("/api/user/register")
    Call<RegisterResponseModel> registerUser(@Part("username")RequestBody user ,
                                             @Part("email")RequestBody email,
                                             @Part("password")RequestBody password,
                                             @Part("confirm_password")RequestBody confirmPassword,
                                             @Part("phone_number")RequestBody phone,
                                             @Part("type_of_user")RequestBody typeOfUser,
                                             @Part("gender")RequestBody gender,
                                             @Part MultipartBody.Part image);

    //for login
    @POST("/api/user/login")
    Call<LoginResponseModel> loginUser(@Body LoginModel model);

    //for add repair contact
    @POST("/AddRepair/repair/")
    Call<AddRepairContactModel> addRepairContact(@Header("Authorization") String authToken ,@Body AddRepairContactModel model);

    @GET("/AddRepair/repair/")
    Call<List<AddRepairContactModel>> getRepairContacts(@Header("Authorization") String authToken);

    //for add property
    @Multipart
    @POST("/Property/prop/")
    Call<AddPropertyModel> addProperty(@Header("Authorization") String authToken,@Part("propertyName")RequestBody propertyName ,
                                       @Part("totalRoom")RequestBody totalRoom,
                                       @Part("propertyCapacity")RequestBody propertyCapacity,
                                       @Part("address1")RequestBody address1,
                                       @Part("address2")RequestBody address2,
                                       @Part("state")RequestBody state,
                                       @Part("postCode")RequestBody postCode,
                                       @Part("city")RequestBody city,
                                       @Part("description")RequestBody description,
                                       @Part MultipartBody.Part image );

    //for getting property
    @GET("/Property/prop/")
    Call<List<AddPropertyModel>> getProperty(@Header("Authorization") String authToken);

    //for add payment card
    @POST("api/user/payment/")
    Call<AddPaymentModel> addPaymentCard(@Header("Authorization") String authToken ,@Body AddPaymentModel model);

    //for payment transaction
    //@POST("api/user/paymentapi/")
   // Call<AddPaymentModel> addPaymentTransaction(@Header("Authorization") String authToken ,@Body PaymentTransactionModel model);

    @POST("/api/user/SendResetPasswordEmail")
    Call<SendResetModel> sendReset(@Body SendResetModel model);

    @POST("")
    Call<EditProfileModel> addRepairContact(@Body EditProfileModel model);




}
