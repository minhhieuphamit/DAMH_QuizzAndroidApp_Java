package com.example.quizz_androidapp.api;

import com.example.quizz_androidapp.data.model.LoginRequest;
import com.example.quizz_androidapp.data.model.UserResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

}
