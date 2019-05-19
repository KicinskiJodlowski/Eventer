package com.example.eventer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eventer.R;
import com.example.eventer.RetrofitClient;
import com.example.eventer.SharedPreferenceManager;
import com.example.eventer.fragment.LoginFragment;
import com.example.eventer.model.EventModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitialActivity extends AppCompatActivity {


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferenceManager.init(getApplicationContext());
        Intent activityIntent;

        if (checkTokenIsValid() == true) {

            activityIntent = new Intent(this, MainActivity.class);

        } else {

            activityIntent = new Intent(this, LoginActivity.class);

        }
        startActivity(activityIntent);
        finish();

    }

    private boolean checkTokenIsValid() {

        String session = SharedPreferenceManager.read(SharedPreferenceManager.TOKEN, "");
        if (session.equals("") || session == null) {
            return false;
        } else {
            //SharedPreferenceManager.write(SharedPreferenceManager.TOKEN, "Bearer " +"JakiToZlyTokeneyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySUQiOiJhOTg4NTE5NS1hMTQzLTRhYTAtYjFhMS1iMzhjOGMzYWQ2MmMiLCJuYmYiOjE1NTgyNzQ5NjEsImV4cCI6MT");
            return true;
//            //tu spr czy rzuca unauthorized dla tego tokena
//            Call<ArrayList<EventModel>> call = RetrofitClient.getInstance().getApi().getEvents(SharedPreferenceManager.read(SharedPreferenceManager.TOKEN,""));
//            call.enqueue(new Callback<ArrayList<EventModel>>() {
//                @Override
//                public void onResponse(Call<ArrayList<EventModel>> call, Response<ArrayList<EventModel>> response) {
//
//                }
//
//                @Override
//                public void onFailure(Call<ArrayList<EventModel>> call, Throwable t) {
////                    if (response.code() == 401 ){
////                        SharedPreferenceManager.remove(SharedPreferenceManager.TOKEN);
////                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerLogin, new LoginFragment()).commit();
////                    }
//                }
//            });
//            return true;
        }
//        return false;
    }


}

