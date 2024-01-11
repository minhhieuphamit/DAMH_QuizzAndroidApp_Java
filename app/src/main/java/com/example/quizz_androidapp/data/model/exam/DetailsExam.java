package com.example.quizz_androidapp.data.model.exam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailsExam implements Serializable {

    @SerializedName("exam")
    private Exam exam;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public DetailsExam(Exam exam) {
        this.exam = exam;
    }
}
