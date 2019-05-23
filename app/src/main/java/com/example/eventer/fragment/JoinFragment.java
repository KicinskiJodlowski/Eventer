package com.example.eventer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.RetrofitClient;
import com.example.eventer.SharedPreferenceManager;
import com.example.eventer.activity.MainActivity;
import com.example.eventer.model.QrCodeModel;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.join_event_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button qrInputbtn = view.findViewById(R.id.qrInputBtn);
        Button qrScanBtn = view.findViewById(R.id.qrScanBtn);
        final Button qrSubmitBtn = view.findViewById(R.id.qrSubmitBtn);
        final EditText qrEditText = view.findViewById(R.id.qrTextEdit);

        qrInputbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrEditText.setVisibility(View.VISIBLE);
                qrSubmitBtn.setVisibility(View.VISIBLE);
            }
        });

        qrScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setOrientationLocked(true);
                integrator.setPrompt("Zeskanuj kod wydarzenia");
                //do wyłączenie później
                integrator.setBeepEnabled(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });

        qrSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QrCodeModel qrCodeModel = new QrCodeModel(qrEditText.getText().toString());
                Call<String> call = RetrofitClient.getInstance().getApi().addToEvent(SharedPreferenceManager.read(SharedPreferenceManager.TOKEN, ""), qrCodeModel);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 400) {
                            try {
                                Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        if (response.code() == 200) {
                            Toast.makeText(getActivity(), "Dodano do wydarzenia", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getActivity(),"Nie udało się dołączyć do wydarzenia", Toast.LENGTH_LONG).show();
                    }
                });
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyEventsFragment()).commit();
            }
        });

    }
}