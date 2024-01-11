package com.example.quizz_androidapp.data.model.result;

import com.google.gson.annotations.SerializedName;

public class ResultResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private Result data;

    public ResultResponse(int status, Result data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Result getData() {
        return data;
    }

    public void setData(Result data) {
        this.data = data;
    }

}
