package com.example.quizz_androidapp.data.model.logout;

import com.google.gson.annotations.SerializedName;

public class LogoutResponse {
    public LogoutResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    private String message;
}
