package com.example.eventer.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.RetrofitClient;
import com.example.eventer.SharedPreferenceManager;
import com.example.eventer.activity.InitialActivity;
import com.example.eventer.activity.MainActivity;
import com.example.eventer.model.LoginJSONModel;
import com.example.eventer.model.NotifyRegisterModel;
import com.example.eventer.model.TokenJSONModel;
import com.example.eventer.service.UserAPIClient;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.eventer.activity.InitialActivity.initialActivity;

public class LoginFragment extends Fragment {

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://neweventer.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserAPIClient userAPIClient = retrofit.create(UserAPIClient.class);
    Intent activityIntent;

    public static String userTOKEN;
    public static String userID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View loginView = inflater.inflate(R.layout.login_fragment, container, false);

        return loginView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button registerViewButton = view.findViewById(R.id.RegisterButton);
        Button loginUserButton = view.findViewById(R.id.loginButton);
        registerViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerLogin, new RegisterFragment()).commit();
            }
        });
        loginUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginOnClick();
                notificationRegister();
            }
        });
    }

    private void loginOnClick() {
        SharedPreferenceManager.write(SharedPreferenceManager.Login, ((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.userNameText)).getText().toString());
        LoginJSONModel login = new LoginJSONModel(((EditText) Objects.requireNonNull(getActivity()).findViewById(R.id.userNameText)).getText().toString(), ((EditText) getActivity().findViewById(R.id.passwordText)).getText().toString());
        Call<TokenJSONModel> call = RetrofitClient.getInstance().getApi().login(login);
        call.enqueue(new Callback<TokenJSONModel>() {
            @Override
            public void onResponse(Call<TokenJSONModel> call, Response<TokenJSONModel> response) {
                if (response.isSuccessful()) {

                    userID = response.body().getId();
                    SharedPreferenceManager.write(SharedPreferenceManager.ID, userID);
                    userTOKEN = "Bearer " + response.body().getToken();
                    SharedPreferenceManager.write(SharedPreferenceManager.TOKEN, userTOKEN);
                    //activityIntent = new Intent(getActivity(), MainActivity.class);
                    //startActivity(activityIntent);
                    //getActivity().finish();

                } else {
                    Toast.makeText(getActivity(), "Login FAIL", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TokenJSONModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Login failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notificationRegister() {
        //GET api/notifications/register get  registerID = 868706491613990126-1129693185868003113-2
//        Call<String> call = RetrofitClient.getInstance().getApi().getRegisterID(SharedPreferenceManager.read(SharedPreferenceManager.TOKEN,""));
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.code() == 400) {
//                    try {
//                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//                if (response.code() == 200) {
//                    SharedPreferenceManager.write(SharedPreferenceManager.RegisterID, response.body().substring(1,response.body().length()-1));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(getActivity(),"Nie udało się zarejestrować w usłudze notyfikacji", Toast.LENGTH_LONG).show();
//            }
//        });

        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(initialActivity.getApplicationContext());
        NotifyRegisterModel notifyRegisterModel = new NotifyRegisterModel(sharedPreferences.getString("FCMtoken", null));

        Call<ResponseBody> call2 = RetrofitClient.getInstance().getApi().notifyRegister(sharedPreferences.getString("registrationID",""),
                SharedPreferenceManager.read(SharedPreferenceManager.TOKEN,""), notifyRegisterModel);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("RegID", "Rejestracja do notyfikacji udana");
                    activityIntent = new Intent(getActivity(), MainActivity.class);
                    startActivity(activityIntent);
                    //getActivity().finish();
                }
                else Log.d("RegID", "Rejestracja do notyfikacji nieudana");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("RegID", "Failure request");
            }
        });
        Toast.makeText(getActivity(), call2.toString(), Toast.LENGTH_LONG).show();
        //PUT api/notifications/enable/{registerID}
//        {
//            "Platform":"gcm",
//                "Handle":"di4QGaaWBo0:APA91bHl5DZna0Kx8cfN2xDvNGocvFkoxw0wsl6YU2hT0vHIrT87_gtouxM6sNdBx2E5vFfjQBM68BkkdsdFwzbyvxeHblBrPULwfND_vflfmDjj7JoZJQ-q-tM8GkndIlFjinKfF-5P",
//                "Tags":["Rafal"]
//        }
    }

}

