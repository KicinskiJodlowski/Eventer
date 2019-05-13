package com.example.eventer.service;

import com.example.eventer.model.LoginJSONModel;
import com.example.eventer.model.TokenJSONModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserLogin {

    @POST("/api/AppUser/login")
    Call<TokenJSONModel> login(@Body LoginJSONModel login);
}
