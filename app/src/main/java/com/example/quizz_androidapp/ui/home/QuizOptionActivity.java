package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizz_androidapp.R;

public class QuizOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_option);

        backToHome();
        doMathTest();

    }

    private void backToHome(){
        findViewById(R.id.imageViewQuizOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void doMathTest(){
        findViewById(R.id.cvMath).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizOptionActivity.this, MathQuizActivity.class);
                startActivity(intent);
            }
        });
    }
}