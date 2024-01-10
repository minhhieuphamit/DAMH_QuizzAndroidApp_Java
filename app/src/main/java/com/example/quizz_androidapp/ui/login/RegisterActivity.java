package com.example.quizz_androidapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.api.APIService;
import com.example.quizz_androidapp.data.model.login.LoginRequest;
import com.example.quizz_androidapp.data.model.login.RegisterRequest;
import com.example.quizz_androidapp.data.model.login.User;
import com.example.quizz_androidapp.data.model.login.UserResponse;
import com.example.quizz_androidapp.ui.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText et_register_first_name, et_register_last_name, et_register_email, et_register_password;
    Button btn_submit_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        backToPrevious();

        btn_submit_register.setOnClickListener(view -> register());

    }

    private void backToPrevious(){
        findViewById(R.id.imageRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initView(){
        et_register_last_name = findViewById(R.id.et_LastName);
        et_register_first_name = findViewById(R.id.et_FirstName);
        et_register_email = findViewById(R.id.et_EmailRegister);
        et_register_password = findViewById(R.id.et_PasswordRegister);
        btn_submit_register = findViewById(R.id.btn_SubmitRegister);
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng ký thành công");
        builder.setMessage("Kiểm tra email để kích hoạt tài khoản của bạn.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void register(){
        String enteredLN = et_register_last_name.getText().toString().trim();
        String enteredFN = et_register_first_name.getText().toString().trim();
        String enteredEmail = et_register_email.getText().toString().trim();
        String enteredPassword = et_register_password.getText().toString().trim();

        if (enteredEmail.isEmpty() || enteredPassword.isEmpty() || enteredLN.isEmpty() || enteredFN.isEmpty()) {
            Toast.makeText(this, "Điền đủ cả 4 thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(enteredEmail)) {
            Toast.makeText(this, "Hãy nhập đúng định dạng email", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<UserResponse> call = APIService.apiService.register(new RegisterRequest(enteredFN,enteredLN,enteredEmail,enteredPassword));
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    showSuccessDialog();
                }else {
                    int statusCode = response.code();
                    String message = response.message();
                    Log.e("Message", "Message: " + message);
                    Log.e("APIError", "HTTP Status Code: " + statusCode);
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Kết nối sever thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}