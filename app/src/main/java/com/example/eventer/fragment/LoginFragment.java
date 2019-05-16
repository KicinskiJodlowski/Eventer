package com.example.eventer.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.SharedPreferenceManager;
import com.example.eventer.activity.LoginActivity;
import com.example.eventer.activity.MainActivity;
import com.example.eventer.model.LoginJSONModel;
import com.example.eventer.model.TokenJSONModel;
import com.example.eventer.service.UserAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {


    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://neweventer.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserAPIClient userAPIClient = retrofit.create(UserAPIClient.class);
    SharedPreferences token;
    SharedPreferences.Editor editor;
    Intent activityIntent;

    public static String userTOKEN;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View loginView = inflater.inflate(R.layout.login_fragment, container, false);

        return loginView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button registerButton = (Button) view.findViewById(R.id.RegisterButton);
        Button loginButton = (Button) view.findViewById(R.id.loginButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerLogin, new RegisterFragment()).commit();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginOnClick();
            }
        });
    }


    private void loginOnClick() {
        LoginJSONModel login = new LoginJSONModel(((TextView) getActivity().findViewById(R.id.userNameText)).getText().toString(), ((TextView) getActivity().findViewById(R.id.passwordText)).getText().toString());
        Call<TokenJSONModel> call = userAPIClient.login(login);
        call.enqueue(new Callback<TokenJSONModel>() {
            @Override
            public void onResponse(Call<TokenJSONModel> call, Response<TokenJSONModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), response.body().getToken(), Toast.LENGTH_SHORT).show();
                    //tu zapisac token do SP i zakonczyc activity
                    userTOKEN = "Bearer " + response.body().getToken();
                    SharedPreferenceManager.write(SharedPreferenceManager.TOKEN, userTOKEN);
                    activityIntent = new Intent(getActivity(), MainActivity.class);
                    startActivity(activityIntent);
                    getActivity().finish();

                } else {
                    Toast.makeText(getActivity(), "Login FAIL", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TokenJSONModel> call, Throwable t) {
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

