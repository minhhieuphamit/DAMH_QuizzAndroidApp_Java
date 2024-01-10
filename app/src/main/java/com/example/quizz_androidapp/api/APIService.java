package com.example.quizz_androidapp.api;

import com.example.quizz_androidapp.data.model.exam.ExamRequest;
import com.example.quizz_androidapp.data.model.exam.ExamResponse;
import com.example.quizz_androidapp.data.model.login.LoginRequest;
import com.example.quizz_androidapp.data.model.login.RegisterRequest;
import com.example.quizz_androidapp.data.model.login.UserResponse;
import com.example.quizz_androidapp.data.model.question.QuestionResponse;
import com.example.quizz_androidapp.data.model.subject.SubjectResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    //Link: https://hptgroup.me/api/v1/subjects
    @GET("subjects")
    Call<SubjectResponse> getAllSubjects(@Header("Authorization") String authorization);

    //Link: https://hptgroup.me/api/v1/exams
    @POST("exams")
    Call<ExamResponse> createExam(@Header("Authorization") String authorization, @Body ExamRequest examRequest);

    // Link: https://hptgroup.me/api/v1/questions
    @GET("questions")
    Call<QuestionResponse> getAllQuestions(
            @Header("Authorization") String authorization,
            @Query("page") int page,
            @Query("limit") int limit
    );
}
