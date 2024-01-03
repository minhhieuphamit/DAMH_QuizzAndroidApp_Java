package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.ui.login.LoginActivity;

public class HomeActivity extends AppCompatActivity {

    CardView cvStartQuiz, cvRule, cvHistory, cvLogout, cvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startQuizOption();
        logout();
    }

    public void initView() {
        cvStartQuiz = findViewById(R.id.cvStartQuiz);
        cvRule = findViewById(R.id.cvRule);
        cvAbout = findViewById(R.id.cvEditPassword);
        cvHistory = findViewById(R.id.cvHistory);
        cvLogout = findViewById(R.id.cvLogout);
    }

    private void startQuizOption(){
        cvStartQuiz.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, QuizOptionActivity.class));
        });
    }
    private void logout(){
        cvLogout.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.activity_exit,findViewById(R.id.container),false);
            dialog.setContentView(view);
            view.findViewById(R.id.button_exit_no).setOnClickListener(view1->{
                dialog.cancel();
                dialog.dismiss();
            });
            view.findViewById(R.id.button_exit_yes).setOnClickListener(view2->{
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            });
            dialog.show();

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        });
    }

}