package com.example.eventer.model;

import com.google.gson.annotations.SerializedName;

public class LoginJSONModel {
    @SerializedName("UserName")
    private String UserName;
    @SerializedName("Password")
    private String Password;

    public LoginJSONModel(String userName, String password) {
        UserName = userName;
        Password = password;
    }


}
