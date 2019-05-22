package com.example.eventer.service;

import com.example.eventer.model.EventJSONModel;
import com.example.eventer.model.EventModel;
import com.example.eventer.model.GuestModel;
import com.example.eventer.model.LoginJSONModel;
import com.example.eventer.model.NotifyRegisterModel;
import com.example.eventer.model.QrCodeModel;
import com.example.eventer.model.RegisterResponseModel;
import com.example.eventer.model.TokenJSONModel;
import com.example.eventer.model.UserJSONModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("api/Event/{id}")
    Call<EventModel> getEvent(
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

    @Headers( { "Content-Type: application/json" } )
    @POST("api/Event/inviteQR")
    Call<String> addToEvent(
            @Header("Authorization") String authKey,
            @Body QrCodeModel qrCodeModel
            );

    //Notyfikacje
    @GET ("api/notifications/register")
    Call<String> getRegisterID(
            @Header("Authorization") String authKey
            );

    @PUT ("api/notifications/enable/{registerID}")
    Call<ResponseBody> notifyRegister(
            @Path("registerID") String registerID,
            @Header("Authorization") String authKey,
            @Body NotifyRegisterModel notifyRegistermodel
    );

    @DELETE ("api/notifications/unregister/{registerID}")
    Call<ResponseBody> notifyUnregister(
            @Path("registerID") String registerID,
            @Header("Authorization") String authKey
    );
}
