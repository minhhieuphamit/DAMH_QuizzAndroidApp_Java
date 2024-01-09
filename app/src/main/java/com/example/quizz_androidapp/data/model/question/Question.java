package com.example.quizz_androidapp.data.model.question;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

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

    @SerializedName("id")
    private String id;

    public Question(String subjectId, String content, List<String> answer, String difficulty, String correctAnswer, String id) {
        this.subjectId = subjectId;
        this.content = content;
        this.answer = answer;
        this.difficulty = difficulty;
        this.correctAnswer = correctAnswer;
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getContent() {
        return content;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getId() {
        return id;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setId(String id) {
        this.id = id;
    }
}
