package com.example.quizz_androidapp.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.api.APIService;
import com.example.quizz_androidapp.data.model.login.LoginRequest;
import com.example.quizz_androidapp.data.model.login.User;
import com.example.quizz_androidapp.data.model.login.UserResponse;
import com.example.quizz_androidapp.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button btnLogin, btnRegister;

    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_Email);
        password = findViewById(R.id.et_Password);
        btnLogin = findViewById(R.id.btn_Login);
        btnRegister = findViewById(R.id.btn_Register);

        btnLogin.setOnClickListener(view -> loginUser());
        btnRegister.setOnClickListener(View -> registerUser());

    }

    private void registerUser() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void saveTokenToSharedPreferences(String token) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("accessToken", token);
        editor.apply();
    }
    private void loginUser() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang đăng nhập...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String enteredEmail = email.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();

        if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(this, "Điền đủ cả 2 thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(enteredEmail)) {
            progressDialog.dismiss();
            Toast.makeText(this, "Hãy nhập đúng định dạng email", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<UserResponse> call = APIService.apiService.login(new LoginRequest(enteredEmail,enteredPassword));
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    User user = response.body().getData();
                    if (user != null) {
                        String accessToken = response.body().getToken().getAccess().getToken();
                        saveTokenToSharedPreferences(accessToken);
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
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Kết nối sever thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


}