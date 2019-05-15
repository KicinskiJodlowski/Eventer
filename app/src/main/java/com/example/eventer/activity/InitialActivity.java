package com.example.eventer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eventer.fragment.LoginFragment;

public class InitialActivity extends AppCompatActivity {


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent activityIntent;

        // go straight to main if a token is stored

        if (checkTokenIsValid() == true) {

            activityIntent = new Intent(this, MainActivity.class);

        } else {

            activityIntent = new Intent(this, LoginActivity.class);

        }

        startActivity(activityIntent);

        finish();

    }

    private boolean checkTokenIsValid() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("token", Context.MODE_PRIVATE);
        String session = sharedPreferences.getString("token", null);
        if (session == null) {
            return false;
        } else {
            //tu spr czy rzuca unauthorized dla tego tokena
            if (true) {
                LoginFragment.userTOKEN = "Bearer " + session; return true;
            } else {
                LoginFragment.userTOKEN = "Bearer " + session; return true;}
        }
//        return false;
    }


}

