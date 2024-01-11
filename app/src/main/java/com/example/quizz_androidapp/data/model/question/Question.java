package com.example.quizz_androidapp.data.model.question;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    public Question(String subjectId, String content, List<String> answer, String difficulty, String correctAnswer, String id, String createAt, String updateAt) {
        this.subjectId = subjectId;
        this.content = content;
        this.answer = answer;
        this.difficulty = difficulty;
        this.correctAnswer = correctAnswer;
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    @SerializedName("_id")
    private String id;

    @SerializedName("subject_id")
    private String subjectId;

    @SerializedName("content")
    private String content;

    @SerializedName("answer")
    private List<String> answer;

    @SerializedName("difficulty")
    private String difficulty;

    @SerializedName("correct_answer")
    private String correctAnswer;

    @SerializedName("createdAt")
    private String createAt;

    @SerializedName("updatedAt")
    private String updateAt;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
