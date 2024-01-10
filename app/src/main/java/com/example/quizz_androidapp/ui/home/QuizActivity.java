    package com.example.quizz_androidapp.ui.home;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.content.ContextCompat;

    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
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
    import com.example.quizz_androidapp.api.APIService;
    import com.example.quizz_androidapp.data.model.question.Question;
    import com.example.quizz_androidapp.data.model.question.QuestionResponse;

    import java.util.ArrayList;
    import java.util.List;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
        private CountDownTimer countDownTimer;
        private int mCurrentPosition = 1;
        private ArrayList<Question> mQuestionList;
        private int mSelectedOptionNumber = 0;
        private int correctQuestion = 0;
        ProgressBar progressBar ;
        TextView tvProgressBar, tvQuestion, tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour, tvTimer;
        Button btnSubmit;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quiz);
            initView();
            backToPrevious();

            long countdownMillis = 600000;
            startCountdownTimer(countdownMillis);

            Bundle bundleReceive = getIntent().getExtras();
            if(bundleReceive != null){
                mQuestionList = (ArrayList<Question>) bundleReceive.get("list question");
                setQuestion();
            }


//            Nhận subjectId từ Intent
//            String subjectId = getIntent().getStringExtra("subjectId");
//            Gọi API để lấy danh sách câu hỏi
//            getQuestions(subjectId);
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
                    showExitConfirmationDialog();
                }
            });
        }

        private void goResult(){
            findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        private String getAccessToken() {
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            return preferences.getString("accessToken", null);
        }

        private void setQuestion() {
            defaultOptionView();

            Question question = mQuestionList.get(mCurrentPosition - 1);

            tvQuestion.setText(question.getContent());

            progressBar.setProgress(mCurrentPosition);
            tvProgressBar.setText(mCurrentPosition + "/" + progressBar.getMax());

            List<String> answers = question.getAnswer();
            if (answers.size() >= 4) {
                tvOptionOne.setText(answers.get(0));
                tvOptionTwo.setText(answers.get(1));
                tvOptionThree.setText(answers.get(2));
                tvOptionFour.setText(answers.get(3));
            }

            tvOptionOne.setOnClickListener((View.OnClickListener) this);
            tvOptionTwo.setOnClickListener((View.OnClickListener) this);
            tvOptionThree.setOnClickListener((View.OnClickListener) this);
            tvOptionFour.setOnClickListener((View.OnClickListener) this);
            btnSubmit.setOnClickListener((View.OnClickListener) this);

            if (mCurrentPosition == mQuestionList.size()) {
                btnSubmit.setText("Hoàn thành bài thi");
                goResult();
            } else {
                btnSubmit.setText("Nộp bài");
            }
        }

        @Override
        public void onClick(View v) {
            if (v != null) {
                int viewId = v.getId();

                if (viewId == R.id.tv_optionOne) {
                    if (tvOptionOne != null) {
                        selectedOptionView(tvOptionOne, mQuestionList.get(mCurrentPosition - 1).getAnswer().get(0));
                    }
                } else if (viewId == R.id.tv_optionTwo) {
                    if (tvOptionTwo != null) {
                        selectedOptionView(tvOptionTwo, mQuestionList.get(mCurrentPosition - 1).getAnswer().get(1));
                    }
                } else if (viewId == R.id.tv_optionThree) {
                    if (tvOptionThree != null) {
                        selectedOptionView(tvOptionThree, mQuestionList.get(mCurrentPosition - 1).getAnswer().get(2));
                    }
                } else if (viewId == R.id.tv_optionFour) {
                    if (tvOptionFour != null) {
                        selectedOptionView(tvOptionFour, mQuestionList.get(mCurrentPosition - 1).getAnswer().get(3));
                    }
                } else if (viewId == R.id.btn_submit) {
                    if (mSelectedOptionNumber == 0) {
                        mCurrentPosition++;
                        if (mCurrentPosition <= mQuestionList.size()) {
                            setQuestion();
                        } else {
                            Toast.makeText(this, "You're Done", Toast.LENGTH_SHORT).show();
                           // TODO: Có thể thêm hành động sau khi hoàn thành tất cả câu hỏi
                            // SCORE
                            // iD USER -> hISTORY
                        }
                    } else {
                        Question question = mQuestionList.get(mCurrentPosition - 1);
                        if (question.getCorrectAnswer().equals(mQuestionList.get(mCurrentPosition - 1).getAnswer().get(mSelectedOptionNumber - 1))) {
                            answerView(mSelectedOptionNumber, R.drawable.correct_option_choice);
                            correctQuestion++;
                        } else {
                            answerView(mSelectedOptionNumber, R.drawable.wrong_option_choice);
                        }
                        if (mCurrentPosition == mQuestionList.size()) {
                            btnSubmit.setText("Hoàn thành bài thi");
                            goResult();
                        } else {
                            btnSubmit.setText("Câu tiếp theo");
                        }
                        mSelectedOptionNumber = 0;
                    }
                }
            }
        }

        // ///////////////////////////////// 3 func dưới là set up trạng thái bình thường và khi selected và trạng thái trả lời câu hỏi //////////////////////////
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

        private void selectedOptionView(TextView tv, String selectedAnswer) {
            defaultOptionView();
            mSelectedOptionNumber = mQuestionList.get(mCurrentPosition - 1).getAnswer().indexOf(selectedAnswer) + 1;
            tv.setTextColor(Color.parseColor("#363A43"));
            tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
            tv.setBackground(ContextCompat.getDrawable(this, R.drawable.option_choice_selected));
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

        // ////////////////////////// Hiển thị 1 dialog thông báo thoát //////////////////////////////////////////////
        private void showExitConfirmationDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Bạn có muốn thoát khỏi bài thi này không? Mọi tiến trình bài thi sẽ bị hủy")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Đóng dialog và kết thúc Activity nếu người dùng chọn "Có"
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Đóng dialog nếu người dùng chọn "Không"
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

        // /////////////////////////////////// 3 func ngay dưới là điếm ngược thời gian ///////////////////////////////////
        private void startCountdownTimer(long millisInFuture) {
            countDownTimer = new CountDownTimer(millisInFuture, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimerText(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    tvTimer.setText("00:00");
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    startActivity(intent);
                }
            }.start();
        }

        private void updateTimerText(long millisUntilFinished) {
            int seconds = (int) (millisUntilFinished / 1000);
            String timeLeftFormatted = String.format("%02d:%02d", seconds / 60, seconds % 60);
            tvTimer.setText(timeLeftFormatted);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }

//        private void getQuestions(String subjectId) {
//            String accessToken = getAccessToken();
//            Call<QuestionResponse> call = APIService.apiService.getAllQuestions("Bearer " + accessToken, 1, 10);
//            call.enqueue(new Callback<QuestionResponse>() {
//                @Override
//                public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
//                    if (response.isSuccessful()) {
//                        QuestionResponse questionResponse = response.body();
//                        if (questionResponse != null) {
//                            // Lưu danh sách câu hỏi và hiển thị câu hỏi đầu tiên
//                            mQuestionList = new ArrayList<>(questionResponse.getResults());
//                            setQuestion();
//                        } else {
//                            // Xử lý trường hợp không có câu hỏi trả về
//                        }
//                    } else {
//                        // Xử lý trường hợp lỗi từ API
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<QuestionResponse> call, Throwable t) {
//                    // Xử lý trường hợp lỗi kết nối hoặc lỗi từ API
//                }
//            });
//        }

    }