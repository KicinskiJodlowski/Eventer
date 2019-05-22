package com.example.eventer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventer.R;
import com.google.zxing.integration.android.IntentIntegrator;

public class JoinFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Zeskanuj kod wydarzenia");
        //do wyłączenie później
        integrator.setBeepEnabled(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
        return inflater.inflate(R.layout.join_event_fragment, container, false);
    }




}
