package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.data.model.exam.Exam;
import com.example.quizz_androidapp.data.model.question.Question;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    TextView tvSubject, tvCorrect, tvWrong, tvWelldone;
    String subject, name;
    int correct, wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView();
        backToMain();
        backToPrevious();

        Bundle bundleReceive = getIntent().getExtras();
        if(bundleReceive != null){
            correct = bundleReceive.getInt("correct answer");
            wrong = bundleReceive.getInt("wrong answer");
            subject = (String) bundleReceive.get("subject name");
            name = (String) bundleReceive.get("user first name");
        }
        tvSubject.setText(subject);
        tvCorrect.setText(String.valueOf(correct));
        tvWrong.setText(String.valueOf(wrong));
        String tks = "Làm tốt lắm, " + name;
        tvWelldone.setText(tks);
    }

    public void initView(){
        tvSubject = findViewById(R.id.tv_subject_result);
        tvCorrect = findViewById(R.id.tv_correct_result);
        tvWrong = findViewById(R.id.tv_wrong_result);
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
}