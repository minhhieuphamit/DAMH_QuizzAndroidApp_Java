package com.example.quizz_androidapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.api.APIService;
import com.example.quizz_androidapp.data.model.LoginRequest;
import com.example.quizz_androidapp.data.model.User;
import com.example.quizz_androidapp.data.model.UserResponse;
import com.example.quizz_androidapp.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_Email);
        password = findViewById(R.id.et_Password);
        btnLogin = findViewById(R.id.btn_Login);

        btnLogin.setOnClickListener(view -> loginUser());

    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void loginUser() {
        String enteredEmail = email.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();

        if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(this, "Điền đủ cả 2 thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(enteredEmail)) {
            Toast.makeText(this, "Hãy nhập đúng định dạng email", Toast.LENGTH_SHORT).show();
            return;
        }


        Call<UserResponse> call = APIService.apiService.login(new LoginRequest(enteredEmail,enteredPassword));
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    User user = response.body().getData();
                    if (user != null) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", user);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode = response.code();
                    Log.e("APIError", "HTTP Status Code: " + statusCode);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Kết nối sever thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

}