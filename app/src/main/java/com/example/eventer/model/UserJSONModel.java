package com.example.eventer.model;

import com.google.gson.annotations.SerializedName;

public class UserJSONModel {

    @SerializedName("UserName")
    private String UserName;
    @SerializedName("Email")
    private String Email;
    @SerializedName("Password")
    private String Password;
    @SerializedName("FullName")
    private String FullName;

    public UserJSONModel(String userName, String email, String password, String fullName) {
        this.UserName = userName;
        this.Email = email;
        this.Password = password;
        this.FullName = fullName;
    }
}
