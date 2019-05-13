package com.example.eventer.model;

import com.google.gson.annotations.SerializedName;

public class TokenJSONModel {

    @SerializedName("token")
    private String token;

    public TokenJSONModel(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

}
