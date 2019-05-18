package com.example.eventer.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.RetrofitClient;
import com.example.eventer.model.RegisterResponseModel;
import com.example.eventer.model.UserJSONModel;
import com.example.eventer.service.UserAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button loginViewButton = view.findViewById(R.id.signInButton);
        Button registerUserButton = view.findViewById(R.id.loginButton);
        loginViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerLogin, new LoginFragment()).commit();
            }
        });
        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerOnClick();
            }
        });
    }

    private void registerOnClick() {
        UserJSONModel user = new UserJSONModel(((EditText) getActivity().findViewById(R.id.userNameText)).getText().toString(),
                ((EditText) getActivity().findViewById(R.id.mailText)).getText().toString(),
                ((EditText) getActivity().findViewById(R.id.passwordText)).getText().toString(),
                ((EditText) getActivity().findViewById(R.id.fullNameText)).getText().toString());
        Call<RegisterResponseModel> call = RetrofitClient.getInstance().getApi().register(user);
        call.enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {

            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Register failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
