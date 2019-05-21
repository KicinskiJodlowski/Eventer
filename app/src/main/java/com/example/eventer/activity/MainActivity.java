package com.example.eventer.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eventer.MyHandler;
import com.example.eventer.R;
import com.example.eventer.RegistrationIntentService;
import com.example.eventer.RetrofitClient;
import com.example.eventer.SharedPreferenceManager;
import com.example.eventer.fragment.AddEventFragment;
import com.example.eventer.fragment.JoinFragment;
import com.example.eventer.fragment.MyEventsFragment;
import com.example.eventer.fragment.SettingsFragment;
import com.example.eventer.model.QrCodeModel;
import com.example.eventer.service.UserAPIClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    public static MainActivity mainActivity;
    public static Boolean isVisible = false;
    private static final String TAG = "MainActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //notification
        mainActivity = this;
        registerWithNotificationHubs();
        MyHandler.createChannelAndHandleNotifications(getApplicationContext());


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyEventsFragment()).commit();
            navigationView.setCheckedItem(R.id.my_events);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.add_event:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddEventFragment()).commit();
                break;
            case R.id.my_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyEventsFragment()).commit();
                break;
            case R.id.join_event:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new JoinFragment()).addToBackStack(null).commit();
//                IntentIntegrator integrator = new IntentIntegrator(this);
//                integrator.setOrientationLocked(false);
//                integrator.setPrompt("Scan Event Code");
//                //do wyłączenie później
//                integrator.setBeepEnabled(true);
//                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
//                integrator.setBarcodeImageEnabled(true);
//                integrator.initiateScan();
                break;

            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
            case R.id.logOut_event:
                SharedPreferenceManager.remove(SharedPreferenceManager.TOKEN);
                Intent activityIntent;
                activityIntent = new Intent(this, LoginActivity.class);
                startActivity(activityIntent);
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void TextToQrCode(String Value) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap("content", BarcodeFormat.QR_CODE, 400, 400);
            ImageView imageViewQrCode = (ImageView) findViewById(R.id.qrCode);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch (Exception e) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                String qrCode = result.getContents();
                joinEventByQr(qrCode);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void joinEventByQr(String qrCode) {
        QrCodeModel qrCodeModel = new QrCodeModel(qrCode);
        Call<String> call = RetrofitClient.getInstance().getApi().addToEvent(SharedPreferenceManager.read(SharedPreferenceManager.TOKEN, ""), qrCodeModel);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 400) {
                    try {
                        Toast.makeText(mainActivity, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(mainActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                if (response.code() == 200){
                    Toast.makeText(mainActivity, "Dodano do wydarzenia", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToastNotify("Nie udało się dołączyć do wydarzenia");
            }
        });
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported by Google Play Services.");
                ToastNotify("This device is not supported by Google Play Services.");
                finish();
            }
            return false;
        }
        return true;
    }

    public void registerWithNotificationHubs() {
        if (checkPlayServices()) {
            // Start IntentService to register this application with FCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    public void ToastNotify(final String notificationMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, notificationMessage, Toast.LENGTH_LONG).show();
//                TextView helloText = (TextView) findViewById(R.id.text_hello);
//                helloText.setText(notificationMessage);
            }
        });
    }


}
