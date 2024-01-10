package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.api.APIService;
import com.example.quizz_androidapp.data.model.exam.Exam;
import com.example.quizz_androidapp.data.model.exam.ExamData;
import com.example.quizz_androidapp.data.model.exam.ExamRequest;
import com.example.quizz_androidapp.data.model.exam.ExamResponse;
import com.example.quizz_androidapp.data.model.login.User;

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
            if(exam != null){
                String subjectExam =  exam.getIdExam();
                findViewById(R.id.btn_StartExam).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
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

}