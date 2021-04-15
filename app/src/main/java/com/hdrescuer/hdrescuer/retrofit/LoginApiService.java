package com.hdrescuer.hdrescuer.retrofit;

import com.hdrescuer.hdrescuer.retrofit.request.RequestLogin;
import com.hdrescuer.hdrescuer.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginApiService {


    @POST("authm/login")
    Call<ResponseAuth> doLogin(@Body RequestLogin requestLogin);


}
