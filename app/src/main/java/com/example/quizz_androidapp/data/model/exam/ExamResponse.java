package com.example.quizz_androidapp.data.model.exam;

import com.google.gson.annotations.SerializedName;

public class ExamResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private ExamData data;

    public ExamResponse(int status, ExamData data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ExamData getData() {
        return data;
    }

    public void setData(ExamData data) {
        this.data = data;
    }

}
