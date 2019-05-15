package com.example.eventer;

import com.example.eventer.service.UserAPIClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://neweventer.azurewebsites.net/";
    private static RetrofitClient retrofitClient;
    private Retrofit retrofit;

    public RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (retrofitClient == null)
            retrofitClient = new RetrofitClient();

        return retrofitClient;
    }

    public UserAPIClient getApi() {
        return retrofit.create(UserAPIClient.class);
    }
}
