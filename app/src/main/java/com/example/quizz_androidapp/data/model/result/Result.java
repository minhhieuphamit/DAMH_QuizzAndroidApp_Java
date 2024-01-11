package com.example.quizz_androidapp.data.model.result;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("user_id")
    private String userID;
    @SerializedName("examination_id")
    private String examinationID;
    @SerializedName("score")
    private int score;
    @SerializedName("time_end")
    private String timeEnd;
    @SerializedName("id")
    private String id;

    public Result(String userID, String examinationID, int score, String timeEnd, String id) {
        this.userID = userID;
        this.examinationID = examinationID;
        this.score = score;
        this.timeEnd = timeEnd;
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getExaminationID() {
        return examinationID;
    }

    public void setExaminationID(String examinationID) {
        this.examinationID = examinationID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
