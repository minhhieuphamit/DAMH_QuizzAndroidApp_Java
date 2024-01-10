package com.example.quizz_androidapp.data.model.exam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailsExamResponse implements Serializable {

    @SerializedName("status")
    private String idExam;
    @SerializedName("data")
    private DetailsExam data;

    public String getIdExam() {
        return idExam;
    }

    public void setIdExam(String idExam) {
        this.idExam = idExam;
    }

    public DetailsExam getData() {
        return data;
    }

    public void setData(DetailsExam data) {
        this.data = data;
    }

    public DetailsExamResponse(String idExam, DetailsExam data) {
        this.idExam = idExam;
        this.data = data;
    }

}
