package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.retrofit.request.RequestSendData;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;
import com.hdrescuer.hdrescuer.retrofit.response.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApiService {

    /*@GET("api/users")
    Call<List<User>> getAllUsers();*/

    @GET("api/apicomposer/get-users-short")
    Call<List<User>> getAllUsers();

    @GET("api/apicomposer/get-user-details/{id}")
    Call<UserDetails> getUserDetails(@Path("id") String id); //Solo viaja una id

    @POST("api/newuser")
    Call<User> setNewUser(@Body UserDetails userDetails);

    @POST("api/updateuser")
    Call<String> updateUser(@Body UserInfo userInfo);

    @POST("api/datarecovery")
    Call<String> setUserData(@Body RequestSendData userData);



}
