package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.retrofit.response.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthApiService {

    @GET("users")
    Call<List<User>> getAllUsers();
}
