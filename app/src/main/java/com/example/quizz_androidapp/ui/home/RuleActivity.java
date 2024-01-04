package com.example.quizz_androidapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.adapter.RulesAdapter;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class RuleActivity extends AppCompatActivity {

    public static ViewPager viewPager;
    RulesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SpringDotsIndicator springDotsIndicator = findViewById(R.id.spring_dots_indicator);
        ImageView back= findViewById(R.id.imageRule);

        back.setOnClickListener(v -> finish());

        viewPager=findViewById(R.id.viewpager);
        adapter=new RulesAdapter(this);
        viewPager.setAdapter(adapter);
        springDotsIndicator.setViewPager(viewPager);
    }
}