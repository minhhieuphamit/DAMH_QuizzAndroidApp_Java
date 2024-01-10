package com.example.quizz_androidapp.data.model.exam;

import com.google.gson.annotations.SerializedName;

public class ExamRequest  {
    public ExamRequest(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @SerializedName("subjectName")
    private String subjectName;
}
