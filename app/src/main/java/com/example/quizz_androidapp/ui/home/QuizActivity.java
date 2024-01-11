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
    import com.example.quizz_androidapp.data.model.exam.Exam;
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
        int correctAnswer = 0;
        int wrongAnswer = 0;
        ProgressBar progressBar ;
        TextView tvProgressBar, tvQuestion, tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour, tvTimer;
        Button btnSubmit;
        String subjectName, userFN, userID, examID;
        boolean[] optionSelectedState = {false, false, false, false};

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
                Exam exam  = (Exam) bundleReceive.get("exam");
                subjectName = (String) bundleReceive.get("subject name");
                userFN = (String) bundleReceive.get("user first name");
                userID = (String) bundleReceive.get("user id");
                if(exam != null){
                    mQuestionList = (ArrayList<Question>) exam.getQuestions();
                    setQuestion();
                    examID = exam.getIdExam();
                }
            }
        }

        private void initView(){
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
                    wrongAnswer = (10 - correctAnswer);

                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("correct answer",correctAnswer);
                    bundle.putSerializable("wrong answer",wrongAnswer);
                    bundle.putSerializable("subject name", subjectName);
                    bundle.putSerializable("user first name", userFN);
                    bundle.putSerializable("user id", userID);
                    bundle.putSerializable("exam id", examID);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            });
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
                btnSubmit.setText("Câu tiếp theo");
            }
        }

        @Override
        public void onClick(View v) {
            if (v != null) {
                int viewId = v.getId();
                if (viewId == R.id.tv_optionOne || viewId == R.id.tv_optionTwo || viewId == R.id.tv_optionThree || viewId == R.id.tv_optionFour) {
                    handleOptionClick((TextView) v);
                } else if (viewId == R.id.btn_submit) {
                    handleSubmitButtonClick();
                }
            }
        }

        private void handleOptionClick(TextView clickedOption) {
            int optionIndex = getOptionIndex(clickedOption);

            if (!optionSelectedState[optionIndex]) {
                selectedOptionView(clickedOption, mQuestionList.get(mCurrentPosition - 1).getAnswer().get(optionIndex));
                mSelectedOptionNumber = optionIndex + 1;
                optionSelectedState[optionIndex] = true;
            } else {
                defaultOptionView();
                mSelectedOptionNumber = 0;
                optionSelectedState[optionIndex] = false;
            }
        }

        private void handleSubmitButtonClick() {
            if (mSelectedOptionNumber == 0) {
                Toast.makeText(this, "Vui lòng chọn một lựa chọn trước khi đi tiếp.", Toast.LENGTH_SHORT).show();
            } else {
                Question question = mQuestionList.get(mCurrentPosition - 1);
                if (question.getCorrectAnswer().equals(mQuestionList.get(mCurrentPosition - 1).getAnswer().get(mSelectedOptionNumber - 1))) {
                    correctAnswer++;
                }

                mCurrentPosition++;
                if (mCurrentPosition <= mQuestionList.size()) {
                    setQuestion();
                } else {
                    showResultScreen();
                }
                mSelectedOptionNumber = 0;
            }
        }

        private void showResultScreen() {
            btnSubmit.setText("Hoàn thành bài thi");
            goResult();
        }

        private int getOptionIndex(TextView option) {
            if (option.getId() == R.id.tv_optionOne) {
                return 0;
            } else if (option.getId() == R.id.tv_optionTwo) {
                return 1;
            } else if (option.getId() == R.id.tv_optionThree) {
                return 2;
            } else if (option.getId() == R.id.tv_optionFour) {
                return 3;
            } else {
                return -1;
            }
        }


        // ///////////////////////////////// 2 func dưới là set up trạng thái bình thường và khi selected //////////////////////////
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

            for (int i = 0; i < options.size(); i++) {
                TextView option = options.get(i);
                option.setTextColor(Color.parseColor("#7A8089"));
                option.setTypeface(Typeface.DEFAULT);
                option.setBackground(ContextCompat.getDrawable(this, R.drawable.option_choice));
                optionSelectedState[i] = false;
            }
        }

        private void selectedOptionView(TextView tv, String selectedAnswer) {
            defaultOptionView();
            tv.setTextColor(Color.parseColor("#363A43"));
            tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv.setBackground(ContextCompat.getDrawable(this, R.drawable.option_choice_selected));
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


    }