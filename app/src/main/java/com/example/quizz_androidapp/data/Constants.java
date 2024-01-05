package com.example.quizz_androidapp.data;

import com.example.quizz_androidapp.R;
import com.example.quizz_androidapp.data.model.Question;

import java.util.ArrayList;

public class Constants {
    public static final String USER_NAME = "user_name";
    public static final String TOTAL_QUESTION = "total_question";
    public static final String CORRECT_QUESTION = "correct_answer";

    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();

        Question questionOne = new Question(
                1, "2+2",
                "1", "2", "3", "4",
                4
        );
        questionList.add(questionOne);

        Question questionTwo = new Question(
                2, "1+2",
                "1", "2", "3", "4",
                3
        );
        questionList.add(questionTwo);

        Question questionThree = new Question(
                3, "1*2-1",
                "1", "2", "3", "4",
                1
        );
        questionList.add(questionThree);

        Question questionFour = new Question(
                4, "1+2-1",

                "1", "2", "3", "4",
                2
        );
        questionList.add(questionFour);

        Question questionFive = new Question(
                5, "1+2+1",
                "1", "2", "3", "4",
                4
        );
        questionList.add(questionFive);

        Question questionSix = new Question(
                6, "1+2+1",
                "1", "2", "3", "4",
                4
        );
        questionList.add(questionSix);

        Question questionSeven = new Question(
                7, "1+2+1",
                "1", "2", "3", "4",
                4
        );
        questionList.add(questionSeven);

        Question questionEight = new Question(
                8, "1+2+1",
                "1", "2", "3", "4",
                4
        );
        questionList.add(questionEight);

        Question questionNine = new Question(
                9, "1+2+1",
                "1", "2", "3", "4",
                4
        );
        questionList.add(questionNine);

        Question questionTen = new Question(
                10, "1+2+1",
                "1", "2", "3", "4",
                4
        );
        questionList.add(questionTen);

        return questionList;
    }
}
