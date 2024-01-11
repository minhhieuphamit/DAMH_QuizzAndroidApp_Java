package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.api.APIService;
import com.example.quizz_androidapp.data.model.exam.DetailsExamResponse;
import com.example.quizz_androidapp.data.model.exam.Exam;
import com.example.quizz_androidapp.data.model.exam.ExamData;
import com.example.quizz_androidapp.data.model.exam.ExamRequest;
import com.example.quizz_androidapp.data.model.exam.ExamResponse;
import com.example.quizz_androidapp.data.model.login.User;
import com.example.quizz_androidapp.data.model.question.Question;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_test);

        Bundle bundleReceive = getIntent().getExtras();
        if(bundleReceive != null){
            ExamData exam  = (ExamData) bundleReceive.get("exam");
            String subjectName = (String) bundleReceive.get("subject name");
            String userFN = (String) bundleReceive.get("user first name");
            String userID = (String) bundleReceive.get("user id");
            if(exam != null){
                String subjectExamID =  exam.getIdExam();
                String accessToken = getAccessToken();
                if(accessToken != null) {
                    Call<DetailsExamResponse> call = APIService.apiService.getDetailsExam("Bearer " + accessToken, subjectExamID);
                    call.enqueue(new Callback<DetailsExamResponse>() {
                        @Override
                        public void onResponse(Call<DetailsExamResponse> call, Response<DetailsExamResponse> response) {
                            if (response.isSuccessful()) {
                                Exam exam = response.body().getData().getExam();
                                if (exam != null) {
                                    findViewById(R.id.btn_StartExam).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(PreTestActivity.this, QuizActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("exam", exam);
                                            bundle.putSerializable("subject name", subjectName);
                                            bundle.putSerializable("user first name", userFN);
                                            bundle.putSerializable("user id", userID);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<DetailsExamResponse> call, Throwable t) {
                            Log.e("Error", String.valueOf(t));
                            Toast.makeText(PreTestActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        backToPrevious();
    }

    private void backToPrevious(){
        findViewById(R.id.imagePreTest).setOnClickListener(new View.OnClickListener() {
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