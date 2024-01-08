package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.api.APIService;
import com.example.quizz_androidapp.data.model.subject.Subject;
import com.example.quizz_androidapp.data.model.subject.SubjectResponse;
import com.example.quizz_androidapp.ui.home.math.PreTestMathActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizOptionActivity extends AppCompatActivity {
    TextView tvMath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_option);
        tvMath = findViewById(R.id.tv_Mathematics);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllSubjects();
        backToPrevious();
        preMathTest();

    }

    private void backToPrevious(){
        findViewById(R.id.imageViewQuizOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void preMathTest(){
        findViewById(R.id.cvMath).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizOptionActivity.this, PreTestMathActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private String getAccessToken() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("accessToken", null);
    }
    private void getAllSubjects() {
        // Lấy token từ SharedPreferences hoặc cơ sở dữ liệu nếu đã lưu trữ
        String accessToken = getAccessToken();

        if (accessToken != null) {
            Call<SubjectResponse> call = APIService.apiService.getAllSubjects("Bearer " + accessToken);
            call.enqueue(new Callback<SubjectResponse>() {
                @Override
                public void onResponse(Call<SubjectResponse> call, Response<SubjectResponse> response) {
                    if (response.isSuccessful()) {
                        SubjectResponse subjectResponse = response.body();
                        if (subjectResponse != null) {
                            displaySubjects(subjectResponse.getResults());
                        }
                    } else {
                        // Xử lý trường hợp lỗi từ API
                    }
                }

                @Override
                public void onFailure(Call<SubjectResponse> call, Throwable t) {
                    // Xử lý trường hợp lỗi kết nối hoặc lỗi từ API
                }
            });
        } else {
            // Xử lý trường hợp không có token (chưa đăng nhập)
        }
    }


    private void displaySubjects(List<Subject> subjects) {
        if (!subjects.isEmpty()) {
            Subject firstSubject = subjects.get(0);
            tvMath.setText(""+firstSubject.getName());
        }
    }
}