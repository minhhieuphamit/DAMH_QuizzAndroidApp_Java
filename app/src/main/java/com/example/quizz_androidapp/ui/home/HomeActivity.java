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
import com.example.quizz_androidapp.data.model.login.User;
import com.example.quizz_androidapp.ui.login.LoginActivity;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    CardView cvStartQuiz, cvRule, cvHistory, cvLogout, cvAbout;
    TextView tvUserName;
    String userFN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

        Bundle bundleReceive = getIntent().getExtras();
        if(bundleReceive != null){
            User user = (User) bundleReceive.get("user");
            if(user != null){
                String userFL = "Xin chào, " + user.getLastName() + " " + user.getFirstName();
                userFN = user.getFirstName();
                tvUserName.setText(userFL);
                clickInfo(user);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startQuizOption();
        clickRule();
        clickHistory();
        logout();
    }

    public void initView() {
        cvStartQuiz = findViewById(R.id.cvStartQuiz);
        cvRule = findViewById(R.id.cvRule);
        cvAbout = findViewById(R.id.cvEditPassword);
        cvHistory = findViewById(R.id.cvHistory);
        cvLogout = findViewById(R.id.cvLogout);
        tvUserName = findViewById(R.id.tv_UsernameHome);
    }

    private void startQuizOption(){
        cvStartQuiz.setOnClickListener(view -> {
            Intent intentQuiz = new Intent(HomeActivity.this, QuizOptionActivity.class);
            Bundle bundleQuiz = new Bundle();
            bundleQuiz.putSerializable("user first name", userFN);
            intentQuiz.putExtras(bundleQuiz);
            startActivity(intentQuiz);
        });
    }

    private void clickRule(){
        cvRule.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, RuleActivity.class));
        });
    }

    private void clickInfo(User user){
        cvAbout.setOnClickListener(view -> {
            Intent intentInfo = new Intent(HomeActivity.this, InfoActivity.class);
            Bundle bundleInfo = new Bundle();
            bundleInfo.putSerializable("user info", user);
            intentInfo.putExtras(bundleInfo);
            startActivity(intentInfo);
        });
    }

    private void clickHistory(){
        cvHistory.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
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

            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        });
    }
}