package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.adapter.HistoryAdapter;
import com.example.quizz_androidapp.api.APIService;
import com.example.quizz_androidapp.data.model.exam.ExamData;
import com.example.quizz_androidapp.data.model.result.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Bundle bundleReceive = getIntent().getExtras();
        if(bundleReceive != null) {
            userID = (String) bundleReceive.get("user id");
        }
        loadHistoryList();

    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageView back= findViewById(R.id.imageHistory);
        back.setOnClickListener(v -> finish());
    }

    private String getAccessToken() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("accessToken", null);
    }

    private void loadHistoryList() {
        String accessToken = getAccessToken();

        Call<List<Result>> call = APIService.apiService.getResultByStudentID("Bearer " + accessToken, userID);
        call.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(HistoryActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    List<Result> historyList = response.body();
                    displayHistory(historyList);
                } else {
                    Toast.makeText(HistoryActivity.this, "Không có dữ liệu lịch sử", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                Log.e("Lỗi", String.valueOf(t));
                Toast.makeText(HistoryActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayHistory(List<Result> historyList) {
        RecyclerView recyclerView = findViewById(R.id.rvHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryAdapter historyAdapter = new HistoryAdapter(this, historyList);
        recyclerView.setAdapter(historyAdapter);
    }
}