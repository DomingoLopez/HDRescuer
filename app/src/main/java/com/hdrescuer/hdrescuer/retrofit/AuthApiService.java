package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.retrofit.request.RequestSendData;
import com.hdrescuer.hdrescuer.retrofit.request.RequestUserDetails;
import com.hdrescuer.hdrescuer.retrofit.response.User;
import com.hdrescuer.hdrescuer.retrofit.response.UserDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthApiService {

    @GET("api/users")
    Call<List<User>> getAllUsers();

    @POST("api/userdetails")
    Call<UserDetails> getUserDetails(@Body RequestUserDetails userDetails); //Solo viaja una id

    @POST("api/newuser")
    Call<User> setNewUser(@Body UserDetails userDetails);

    @POST("api/updateuser")
    Call<UserDetails> updateUser(@Body UserDetails userDetails);

    @POST("api/datarecovery")
    Call<String> setUserData(@Body RequestSendData userData);
}
