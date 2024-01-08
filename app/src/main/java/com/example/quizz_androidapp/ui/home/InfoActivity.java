package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.data.model.login.User;

public class InfoActivity extends AppCompatActivity {

    EditText etInfoUsername,etInfoEmail,etInfoPassword;
    Button btnInfoSubmitChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();

        Bundle bundleInfo = getIntent().getExtras();
        if(bundleInfo != null){
            User user = (User) bundleInfo.get("user info");
            if(user != null){
                String username = user.getLastName() + " " + user.getFirstName();
                etInfoUsername.setText(username);
                etInfoEmail.setText(user.getEmail());
                etInfoPassword.setText(user.getPassword());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageView back= findViewById(R.id.imageInfo);
        back.setOnClickListener(v -> finish());
    }

    private void initView(){
        etInfoUsername = findViewById(R.id.et_info_username);
        etInfoEmail = findViewById(R.id.et_info_email);
        etInfoPassword = findViewById(R.id.et_info_password);
        btnInfoSubmitChange = findViewById(R.id.btn_info_submit_change);
    }
}