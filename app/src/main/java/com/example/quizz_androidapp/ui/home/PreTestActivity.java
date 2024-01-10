package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizz_androidapp.R;

public class PreTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_test);
    }

    @Override
    protected void onStart() {
        super.onStart();
        backToPrevious();
        doTest();
    }

    private void backToPrevious(){
        findViewById(R.id.imagePreTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void doTest(){
        findViewById(R.id.btn_StartExam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectId = getIntent().getStringExtra("subjectId");
                Intent intent = new Intent(PreTestActivity.this, QuizActivity.class);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
                finish();
            }
        });
    }
}