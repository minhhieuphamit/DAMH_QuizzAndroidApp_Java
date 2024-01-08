package com.example.quizz_androidapp.data.model.login;

import com.example.quizz_androidapp.data.model.login.User;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private User data;

    @SerializedName("token")
    private TokenInfo token;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getData() {
        return data;
    }

    public TokenInfo getToken() {
        return token;
    }

    public static class TokenInfo {

        @SerializedName("access")
        private AccessTokenInfo access;

        public AccessTokenInfo getAccess() {
            return access;
        }

        public static class AccessTokenInfo {

            @SerializedName("token")
            private String token;

            @SerializedName("expires")
            private String expires;

            public String getToken() {
                return token;
            }

            public String getExpires() {
                return expires;
            }
        }
    }
}

