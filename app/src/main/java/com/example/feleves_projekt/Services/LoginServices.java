package com.example.feleves_projekt.Services;

import com.example.feleves_projekt.Models.Login;
import com.example.feleves_projekt.Models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginServices {

    @POST("login/")
    @Headers({
            "x-api-key: ValidApiKulcs"
    })
    Call<LoginResponse> login(@Body Login data);
}
