package com.example.eventer.service;

import com.example.eventer.model.EventJSONModel;
import com.example.eventer.model.LoginJSONModel;
import com.example.eventer.model.TokenJSONModel;
import com.example.eventer.model.UserJSONModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPIClient {

    @POST("/api/AppUser/login")
    Call<TokenJSONModel> login(@Body LoginJSONModel login);

    @POST("/api/AppUser/Register")
    Call<UserJSONModel> register(@Query("succeeded") boolean response, @Query ("errors") String errors);
    //"succeeded": true,
    //    "errors": []

    //@GET ("api/Event")
    //Call<TokenJSONModel> events(@Body EventJSONModel);
}
