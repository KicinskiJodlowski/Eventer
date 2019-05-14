package com.example.eventer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.fragment.LoginFragment;
import com.example.eventer.fragment.MyEventsFragment;
import com.example.eventer.model.LoginJSONModel;
import com.example.eventer.model.TokenJSONModel;
import com.example.eventer.service.UserAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

//    Retrofit.Builder builder = new Retrofit.Builder()
//            .baseUrl("https://neweventer.azurewebsites.net")
//            .addConverterFactory(GsonConverterFactory.create());
//    Retrofit retrofit = builder.build();
//    UserAPIClient userAPIClient = retrofit.create(UserAPIClient.class);
//    SharedPreferences token;
//    SharedPreferences.Editor editor;
//    Intent activityIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerLogin, new LoginFragment()).commit();



    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }

//    private void login() {
//        LoginJSONModel login = new LoginJSONModel(((TextView) findViewById(R.id.userNameText)).getText().toString(), ((TextView) findViewById(R.id.passwordText)).getText().toString());
//        Call<TokenJSONModel> call = userAPIClient.login(login);
//        call.enqueue(new Callback<TokenJSONModel>() {
//            @Override
//            public void onResponse(Call<TokenJSONModel> call, Response<TokenJSONModel> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(LoginActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
//                    //tu zapisac token do SP i zakonczyc activity
//                    token = getSharedPreferences("token", Context.MODE_PRIVATE);
//                    editor = token.edit();
//                    editor.putString("token", response.body().getToken());
//                    editor.commit();
//                    activityIntent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(activityIntent);
//                    finish();
//
//                } else {
//                    Toast.makeText(LoginActivity.this, "Login FAIL", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<TokenJSONModel> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, "failure", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
