package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.api.APIService;
import com.example.quizz_androidapp.data.model.exam.ExamData;
import com.example.quizz_androidapp.data.model.exam.ExamRequest;
import com.example.quizz_androidapp.data.model.exam.ExamResponse;
import com.example.quizz_androidapp.data.model.subject.Subject;
import com.example.quizz_androidapp.data.model.subject.SubjectResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizOptionActivity extends AppCompatActivity {
    TextView tvDiaLy, tvGDCD, tvLichSu;
    String userFN, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_option);
        initView();

        Bundle bundleReceive = getIntent().getExtras();
        if(bundleReceive != null){
            userFN = (String) bundleReceive.get("user first name");
            userID = (String) bundleReceive.get("user id");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllSubjects();
        backToPrevious();
        preTest();
    }

    private void initView(){
        tvDiaLy = findViewById(R.id.tv_DiaLy);
        tvGDCD = findViewById(R.id.tv_GDCD);
        tvLichSu = findViewById(R.id.tv_LichSu);
    }

    private void backToPrevious(){
        findViewById(R.id.imageViewQuizOption).setOnClickListener(new View.OnClickListener() {
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

    private void getAllSubjects() {
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
                        // TODO: Xử lý trường hợp lỗi từ API
                    }
                }

                @Override
                public void onFailure(Call<SubjectResponse> call, Throwable t) {
                    // TODO: Xử lý trường hợp lỗi kết nối hoặc lỗi từ API
                }
            });
        } else {
            // TODO: Xử lý trường hợp không có token (chưa đăng nhập)
        }
    }

    private void displaySubjects(List<Subject> subjects) {
        if (!subjects.isEmpty()) {
            Subject firstSubject = subjects.get(0);
            Subject secondSubject = subjects.get(1);
            Subject thirdSubject = subjects.get(2);
            tvDiaLy.setText(""+firstSubject.getName());
            tvGDCD.setText(""+secondSubject.getName());
            tvLichSu.setText(""+thirdSubject.getName());
        }
    }

    private void preTest(){
        findViewById(R.id.cvDiaLy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectDiaLy = tvDiaLy.getText().toString();
                createExam(subjectDiaLy);
            }
        });
        findViewById(R.id.cvGDCD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectGDCD = tvGDCD.getText().toString();
                createExam(subjectGDCD);
            }
        });
        findViewById(R.id.cvLichSu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectLichSu = tvLichSu.getText().toString();
                createExam(subjectLichSu);
            }
        });
    }

    private void createExam(String subjectName){
        String accessToken = getAccessToken();
        if(accessToken != null) {
            Call<ExamResponse> call = APIService.apiService.createExam("Bearer " + accessToken, new ExamRequest(subjectName));
            call.enqueue(new Callback<ExamResponse>() {
                @Override
                public void onResponse(Call<ExamResponse> call, Response<ExamResponse> response) {
                    if (response.isSuccessful()) {
                        ExamData exam = response.body().getData();
                        if (exam != null){
                            Log.e("exam", exam.toString());
                            Intent intent = new Intent(QuizOptionActivity.this, PreTestActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("subject name", subjectName);
                            bundle.putSerializable("user first name", userFN);
                            bundle.putSerializable("user id", userID);
                            bundle.putSerializable("exam", exam);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ExamResponse> call, Throwable t) {
                    Log.e("Error", String.valueOf(t));
                    Toast.makeText(QuizOptionActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}