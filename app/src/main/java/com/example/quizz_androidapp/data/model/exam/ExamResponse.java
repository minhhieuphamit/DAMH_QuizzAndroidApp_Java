package com.example.quizz_androidapp.data.model.exam;

import com.google.gson.annotations.SerializedName;

public class ExamResponse {
    public ExamResponse(int status, Exam data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Exam getData() {
        return data;
    }

    public void setData(Exam data) {
        this.data = data;
    }

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private Exam data;
}
