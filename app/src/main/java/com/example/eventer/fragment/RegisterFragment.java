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

import com.example.eventer.R;

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

    }
}
