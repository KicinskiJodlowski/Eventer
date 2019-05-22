package com.example.eventer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.RetrofitClient;
import com.example.eventer.SharedPreferenceManager;
import com.example.eventer.fragment.AddEventFragment;
import com.example.eventer.fragment.JoinFragment;
import com.example.eventer.fragment.MyEventsFragment;
import com.example.eventer.fragment.SettingsFragment;
import com.example.eventer.model.QrCodeModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eventer.activity.InitialActivity.initialActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferenceManager.init(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




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
                SharedPreferences sharedPreferences;
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(initialActivity.getApplicationContext());
                Call<ResponseBody> logout = new RetrofitClient().getApi().notifyUnregister(sharedPreferences.getString("registrationID",""),
                        SharedPreferenceManager.read(SharedPreferenceManager.TOKEN, ""));
                logout.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            //SharedPreferenceManager.remove(SharedPreferenceManager.RegisterID);
                            Log.d("unRegID", "Wyrejestrowanie z usługi udane");
                        } else Log.d("unRegID", "Wyrejestrowanie z usługi nie powiodło się");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("unRegID", "Failure request");
                    }
                });
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
                        Toast.makeText(getBaseContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                if (response.code() == 200) {
                    Toast.makeText(getBaseContext(), "Dodano do wydarzenia", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Nie udało się dołączyć do wydarzenia", Toast.LENGTH_LONG).show();
            }
        });
    }







}
