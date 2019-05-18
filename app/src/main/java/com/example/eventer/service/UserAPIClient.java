package com.example.eventer.service;

import com.example.eventer.model.EventJSONModel;
import com.example.eventer.model.EventModel;
import com.example.eventer.model.GuestModel;
import com.example.eventer.model.LoginJSONModel;
import com.example.eventer.model.RegisterResponseModel;
import com.example.eventer.model.TokenJSONModel;
import com.example.eventer.model.UserJSONModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPIClient {

    String URL = "https://neweventer.azurewebsites.net/";



    @POST("/api/AppUser/login")
    Call<TokenJSONModel> login(@Body LoginJSONModel login);

    @POST("/api/AppUser/Register")
    Call<RegisterResponseModel> register(@Body UserJSONModel user);
    //"succeeded": true,
    //    "errors": []

    @GET("api/Event/userEvent")
    Call<ArrayList<EventModel>> getEvents(@Header("Authorization") String authKey);

    @GET("api/InvitedGuest/eventGuest/{id}")
    Call<ArrayList<GuestModel>> getGuests(
            @Path("id") int id,
            @Header("Authorization") String authKey
    );

    @Headers( { "Content-Type: application/json" } )
    @PUT("api/Event/{id}")
    Call<ResponseBody> updateEvent(
            @Path("id") int id,
            @Header("Authorization") String authKey,
            @Body RequestBody params
    );

    @Headers( { "Content-Type: application/json" } )
    @POST("api/Event")
    Call<ResponseBody> addEvent(
            @Header("Authorization") String authKey,
            @Body RequestBody params
    );

//    @POST("/api/Event")
//    Call<EventJSONModel>  addEvent(@Body EventJSONModel event, @Header("Authorization") String authHeader);

//    @GET("api/Event/GetProfile?id={id}")
//    Call<UserProfile> getUser(@Path("id") String id, @Header("Authorization") String authHeader);


}
