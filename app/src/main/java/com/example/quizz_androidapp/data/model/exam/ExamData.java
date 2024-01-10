package com.example.quizz_androidapp.data.model.exam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExamData implements Serializable {

    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("questions")
    private List<String> questions;
    @SerializedName("id")
    private String idExam;

    public ExamData(String startTime, String endTime, List<String> questions, String idExam) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.questions = questions;
        this.idExam = idExam;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public String getIdExam() {
        return idExam;
    }

    public void setIdExam(String idExam) {
        this.idExam = idExam;
    }

}
