package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.api.APIService;
import com.example.quizz_androidapp.data.model.login.User;
import com.example.quizz_androidapp.data.model.logout.LogoutResponse;
import com.example.quizz_androidapp.ui.login.LoginActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    CardView cvStartQuiz, cvRule, cvHistory, cvLogout, cvAbout;
    TextView tvUserName;
    String userFN, userID;

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
                userID = user.getUserId();
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
            bundleQuiz.putSerializable("user id", userID);
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
            Intent intentHistory = new Intent(HomeActivity.this, HistoryActivity.class);
            Bundle bundleHistory = new Bundle();
            bundleHistory.putSerializable("user id", userID);
            intentHistory.putExtras(bundleHistory);
            startActivity(intentHistory);
        });
    }

    private String getAccessToken() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("accessToken", null);
    }

    private void logout(){
        String accessToken = getAccessToken();
        cvLogout.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.activity_exit,findViewById(R.id.container),false);
            dialog.setContentView(view);
            view.findViewById(R.id.button_exit_no).setOnClickListener(view1->{
                dialog.cancel();
                dialog.dismiss();
            });
            view.findViewById(R.id.button_exit_yes).setOnClickListener(view2->{
                Call<LogoutResponse> call = APIService.apiService.logout("Bearer " + accessToken);
                call.enqueue(new Callback<LogoutResponse>() {
                    @Override
                    public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<LogoutResponse> call, Throwable t) {

                    }
                });
            });
            dialog.show();

            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        });
    }
}