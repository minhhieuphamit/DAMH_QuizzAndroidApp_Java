package com.example.quizz_androidapp.data.model.result;

import com.google.gson.annotations.SerializedName;

public class ResultRequest {

    @SerializedName("user_id")
    private String userID;
    @SerializedName("examination_id")
    private String examID;
    @SerializedName("score")
    private int score;

    public ResultRequest(String userID, String examID, int score) {
        this.userID = userID;
        this.examID = examID;
        this.score = score;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
