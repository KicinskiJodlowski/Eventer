package com.example.eventer.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventer.R;
import com.google.zxing.integration.android.IntentIntegrator;


public class ScanFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Zeskanuj kod wydarzenia");
        //do wyłączenie później
        integrator.setBeepEnabled(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

}
