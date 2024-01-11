package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.api.APIService;
import com.example.quizz_androidapp.data.model.exam.Exam;
import com.example.quizz_androidapp.data.model.question.Question;
import com.example.quizz_androidapp.data.model.result.Result;
import com.example.quizz_androidapp.data.model.result.ResultRequest;
import com.example.quizz_androidapp.data.model.result.ResultResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    TextView tvSubject, tvCorrect, tvWrong, tvWelldone, tvDate;
    String subject, name, userID, examID, date;
    int correct, wrong, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView();
        backToMain();
        backToPrevious();

        Bundle bundleReceive = getIntent().getExtras();
        if(bundleReceive != null) {
            correct = bundleReceive.getInt("correct answer");
            wrong = bundleReceive.getInt("wrong answer");
            subject = (String) bundleReceive.get("subject name");
            name = (String) bundleReceive.get("user first name");

            userID = (String) bundleReceive.get("user id");
            examID = (String) bundleReceive.get("exam id");
            score = correct * 10;
        }
        String accessToken = getAccessToken();

        Call<ResultResponse> call = APIService.apiService.createResult("Bearer " + accessToken, new ResultRequest(userID, examID, score));
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful()){
                    Result result = response.body().getData();
                    if(result != null){
                        Toast.makeText(ResultActivity.this,"Gửi kết quả lên sever thành công",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Log.e("Error", String.valueOf(t));
                Toast.makeText(ResultActivity.this,"Gửi kết quả lên sever thất bại",Toast.LENGTH_SHORT).show();
            }
        });
        tvSubject.setText(subject);
        tvCorrect.setText(String.valueOf(correct));
        tvWrong.setText(String.valueOf(wrong));
//        tvDate.setText(date);
        String tks = "Làm tốt lắm, " + name;
        tvWelldone.setText(tks);
    }

    public void initView(){
        tvSubject = findViewById(R.id.tv_subject_result);
        tvCorrect = findViewById(R.id.tv_correct_result);
        tvWrong = findViewById(R.id.tv_wrong_result);
//        tvDate = findViewById(R.id.tv_date_result);
        tvWelldone = findViewById(R.id.tvWellDone);
    }

    private void backToPrevious(){
        findViewById(R.id.imageViewFinalResultQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void backToMain(){
        findViewById(R.id.btnFinishQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getAccessToken() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("accessToken", null);
    }
}