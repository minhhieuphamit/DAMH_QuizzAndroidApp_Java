package com.example.quizz_androidapp.data.model.exam;

import com.example.quizz_androidapp.data.model.question.Question;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Exam implements Serializable {

    @SerializedName("_id")
    private String idExam;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("questions")
    private List<Question> questions;
    @SerializedName("createdAt")
    private String createAt;
    @SerializedName("updatedAt")
    private String updateAt;
    @SerializedName("__v")
    private int version;

    public String getIdExam() {
        return idExam;
    }

    public void setIdExam(String idExam) {
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "idExam='" + idExam + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", questions=" + questions +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", version=" + version +
                '}';
    }

    public Exam(String idExam, String startTime, String endTime, List<Question> questions, String createAt, String updateAt, int version) {
        this.idExam = idExam;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questions = questions;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.version = version;
    }
}
