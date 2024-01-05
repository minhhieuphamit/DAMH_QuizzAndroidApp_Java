package com.example.quizz_androidapp.ui.home.math;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.data.Constants;
import com.example.quizz_androidapp.data.model.Question;

import java.util.ArrayList;

public class MathQuizActivity extends AppCompatActivity implements View.OnClickListener {

    private CountDownTimer countDownTimer;
    private int mCurrentPosition = 1;
    private ArrayList<Question> mQuestionList;
    private int mSelectedOptionNumber = 0;
    ProgressBar progressBar ;
    TextView tvProgressBar ;
    TextView tvQuestion ;
    TextView tvOptionOne ;
    TextView tvOptionTwo ;
    TextView tvOptionThree ;
    TextView tvOptionFour;
    TextView tvTimer;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_quiz);
        initView();

//        long countdownMillis = 1800000;
        long countdownMillis = 10000;
        startCountdownTimer(countdownMillis);


        mQuestionList = Constants.getQuestions();
        setQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        backToPrevious();
    }

    private void setQuestion() {
        defaultOptionView();
        Question question = mQuestionList.get(mCurrentPosition - 1);

        tvQuestion.setText(question.getQuestion());

        progressBar.setProgress(mCurrentPosition);
        tvProgressBar.setText(mCurrentPosition + "/" + progressBar.getMax());

        tvOptionOne.setText(question.getOptionOne());
        tvOptionTwo.setText(question.getOptionTwo());
        tvOptionThree.setText(question.getOptionThree());
        tvOptionFour.setText(question.getOptionFour());

        tvOptionOne.setOnClickListener((View.OnClickListener) this);
        tvOptionTwo.setOnClickListener((View.OnClickListener) this);
        tvOptionThree.setOnClickListener((View.OnClickListener) this);
        tvOptionFour.setOnClickListener((View.OnClickListener) this);
        btnSubmit.setOnClickListener((View.OnClickListener) this);

        if (mCurrentPosition == mQuestionList.size()) {
            btnSubmit.setText("FINISH");
            goResult();
        } else {
            btnSubmit.setText("SUBMIT");
        }
    }

    private void defaultOptionView() {
        ArrayList<TextView> options = new ArrayList<>();
        if (tvOptionOne != null) {
            options.add(tvOptionOne);
        }
        if (tvOptionTwo != null) {
            options.add(tvOptionTwo);
        }
        if (tvOptionThree != null) {
            options.add(tvOptionThree);
        }
        if (tvOptionFour != null) {
            options.add(tvOptionFour);
        }
        for (TextView i : options) {
            i.setTextColor(Color.parseColor("#7A8089"));
            i.setTypeface(Typeface.DEFAULT);
            i.setBackground(ContextCompat.getDrawable(this, R.drawable.option_choice));
        }
    }

    private void selectedOptionView(TextView tv, int selectedOptionNumber) {
        defaultOptionView();
        mSelectedOptionNumber = selectedOptionNumber;
        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.option_choice_selected));
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            int viewId = v.getId();

            if (viewId == R.id.tv_optionOne) {
                if (tvOptionOne != null) {
                    selectedOptionView(tvOptionOne, 1);
                }
            } else if (viewId == R.id.tv_optionTwo) {
                if (tvOptionTwo != null) {
                    selectedOptionView(tvOptionTwo, 2);
                }
            } else if (viewId == R.id.tv_optionThree) {
                if (tvOptionThree != null) {
                    selectedOptionView(tvOptionThree, 3);
                }
            } else if (viewId == R.id.tv_optionFour) {
                if (tvOptionFour != null) {
                    selectedOptionView(tvOptionFour, 4);
                }
            } else if (viewId == R.id.btn_submit) {
                if (mSelectedOptionNumber == 0) {
                    mCurrentPosition++;
                    if (mCurrentPosition <= mQuestionList.size()) {
                        setQuestion();
                    } else {
                        Toast.makeText(this, "You're Done", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Question question = mQuestionList.get(mCurrentPosition - 1);
                    if (question.getCorrectAnswer() != mSelectedOptionNumber) {
                        answerView(mSelectedOptionNumber, R.drawable.wrong_option_choice);
                    }
                    answerView(question.getCorrectAnswer(), R.drawable.correct_option_choice);

                    if (mCurrentPosition == mQuestionList.size()) {
                        btnSubmit.setText("FINISH");
                        goResult();
                    } else {
                        btnSubmit.setText("NEXT");
                    }
                    mSelectedOptionNumber = 0;
                }
            }
        }
    }

    private void answerView(int answer, int drawableView) {
        switch (answer) {
            case 1:
                if (tvOptionOne != null) {
                    tvOptionOne.setBackground(ContextCompat.getDrawable(this, drawableView));
                }
                break;
            case 2:
                if (tvOptionTwo != null) {
                    tvOptionTwo.setBackground(ContextCompat.getDrawable(this, drawableView));
                }
                break;
            case 3:
                if (tvOptionThree != null) {
                    tvOptionThree.setBackground(ContextCompat.getDrawable(this, drawableView));
                }
                break;
            case 4:
                if (tvOptionFour != null) {
                    tvOptionFour.setBackground(ContextCompat.getDrawable(this, drawableView));
                }
                break;
        }
    }

    public void initView(){
        progressBar = findViewById(R.id.progressBar);
        tvProgressBar = findViewById(R.id.tv_progressBar);
        tvQuestion = findViewById(R.id.tv_question);
        tvOptionOne = findViewById(R.id.tv_optionOne);
        tvOptionTwo = findViewById(R.id.tv_optionTwo);
        tvOptionThree = findViewById(R.id.tv_optionThree);
        tvOptionFour = findViewById(R.id.tv_optionFour);
        btnSubmit = findViewById(R.id.btn_submit);
        tvTimer = findViewById(R.id.tv_timer);
    }

    private void backToPrevious(){
        findViewById(R.id.imageViewQuizMathTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void goResult(){
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MathQuizActivity.this, ResultMathActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void startCountdownTimer(long millisInFuture) {
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Cập nhật TextView với thời gian còn lại
                updateTimerText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // Xử lý khi đếm ngược kết thúc (ví dụ: hiển thị hết giờ)
                tvTimer.setText("00:00");
                Intent intent = new Intent(MathQuizActivity.this, ResultMathActivity.class);
                startActivity(intent);
                // Thực hiện các hành động khi thời gian kết thúc
            }
        }.start();
    }

    private void updateTimerText(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000);
        String timeLeftFormatted = String.format("%02d:%02d", seconds / 60, seconds % 60);
        tvTimer.setText(timeLeftFormatted);
    }

    // Override onDestroy để đảm bảo việc hủy đếm ngược khi Activity bị hủy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}