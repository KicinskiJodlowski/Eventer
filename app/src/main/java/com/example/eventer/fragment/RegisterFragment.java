package com.example.eventer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.RetrofitClient;
import com.example.eventer.model.Error;
import com.example.eventer.model.RegisterResponseModel;
import com.example.eventer.model.UserJSONModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_fragment, container,false);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button loginViewButton = view.findViewById(R.id.signInButton);
        Button registerUserButton = view.findViewById(R.id.loginButton);
        final EditText password = view.findViewById(R.id.passwordText);
        EditText passwordRepeat = view.findViewById(R.id.passwordRepeatText);
        final Editable passwordText = password.getText();
        final Editable passwordRepeatText = passwordRepeat.getText();
        final TextView errorText = getActivity().findViewById(R.id.errorText);

        loginViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerLogin, new LoginFragment()).commit();
            }
        });
        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (passwordRepeatText.toString().equals(passwordText.toString())) {
                    registerOnClick();
                } else errorText.setText("Podane hasła nie są tożsame.");
            }
        });
    }

    private void registerOnClick() {
        UserJSONModel user = new UserJSONModel(((EditText) getActivity().findViewById(R.id.userNameText)).getText().toString(),
                ((EditText) getActivity().findViewById(R.id.mailText)).getText().toString(),
                ((EditText) getActivity().findViewById(R.id.passwordText)).getText().toString(),
                ((EditText) getActivity().findViewById(R.id.fullNameText)).getText().toString());
        final TextView errorText = getActivity().findViewById(R.id.errorText);
        Call<RegisterResponseModel> call = RetrofitClient.getInstance().getApi().register(user);
        call.enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                List<Error> errors = response.body().getErrors();
                if(errors.size()==0){
                errorText.setText("");
                for (Error e : errors) {
                    errorText.append(e.getDescription()+ "\n");
                }}else {
                    Toast.makeText(getActivity(),"Pomyślnie zarejestrowano użytkownika - możesz się teraz zalogować.", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerLogin, new LoginFragment()).commit();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Register failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
