package com.example.eventer.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.model.LoginJSONModel;
import com.example.eventer.model.TokenJSONModel;
import com.example.eventer.service.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://neweventer.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserLogin userLogin = retrofit.create(UserLogin.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button button = findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }

    private void login(){
        LoginJSONModel login = new LoginJSONModel("Rafal", "Rafal12345@");
        Call<TokenJSONModel> call = userLogin.login(login);
        call.enqueue(new Callback<TokenJSONModel>() {
            @Override
            public void onResponse(Call<TokenJSONModel> call, Response<TokenJSONModel> response) {
                Toast.makeText(LoginActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TokenJSONModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
