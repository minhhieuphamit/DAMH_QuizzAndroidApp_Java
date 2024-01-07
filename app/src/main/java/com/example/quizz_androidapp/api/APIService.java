package com.example.quizz_androidapp.api;

import com.example.quizz_androidapp.data.model.login.LoginRequest;
import com.example.quizz_androidapp.data.model.login.RegisterRequest;
import com.example.quizz_androidapp.data.model.login.UserResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    APIService apiService = new Retrofit.Builder()
            .baseUrl("https://hptgroup.me/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService.class);

    //Link: https://hptgroup.me/api/v1/auth/login
    @POST("auth/login")
    Call<UserResponse> login(@Body LoginRequest loginRequest);

    //Link: https://hptgroup.me/api/v1/auth/register
    @POST("auth/register")
    Call<UserResponse> register(@Body RegisterRequest registerRequest);

}
