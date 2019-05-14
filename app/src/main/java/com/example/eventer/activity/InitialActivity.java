package com.example.eventer.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        return true;
    }


}

