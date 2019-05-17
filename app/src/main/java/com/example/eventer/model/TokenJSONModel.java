package com.example.eventer.model;

import com.google.gson.annotations.SerializedName;

public class TokenJSONModel {

    @SerializedName("token")
    private String token;
    @SerializedName("id")
    private String id;

    public TokenJSONModel(String token, String id) {
        this.token = token;
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
