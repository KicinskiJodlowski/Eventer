package com.example.eventer.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.RetrofitClient;
import com.example.eventer.SharedPreferenceManager;
import com.example.eventer.activity.LoginActivity;
import com.example.eventer.activity.MainActivity;
import com.example.eventer.adapter.EventRecordAdapter;
import com.example.eventer.model.EventModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eventer.activity.InitialActivity.initialActivity;

public class MyEventsFragment extends Fragment {

    ListView listViewEvents;

    public static ArrayList<EventModel> listEvents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_events_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewEvents = getActivity().findViewById(R.id.listViewEvents);

        listEvents = new ArrayList<>();
        getEvents();
    }

    private void getEvents() {

        Call<ArrayList<EventModel>> call = RetrofitClient.getInstance().getApi().getEvents(SharedPreferenceManager.read(SharedPreferenceManager.TOKEN, ""));
        call.enqueue(new Callback<ArrayList<EventModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EventModel>> call, Response<ArrayList<EventModel>> response) {

                Log.d("Response Code ", Integer.toString(response.code()));
                if (response.code() == 200) {

                    Toast.makeText(getActivity(), "Pobrano wydarzenia", Toast.LENGTH_SHORT).show();

                    EventRecordAdapter adapter = new EventRecordAdapter(getActivity(), R.layout.event_record, listEvents);
                    listViewEvents.setAdapter(adapter);
                    listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            showEventDetails(position);
                        }
                    });
                    ArrayList<EventModel> events = response.body();

                    for (EventModel event : events) {
                        listEvents.add(event);
                    }
                } else {
                    Toast.makeText(getActivity(), "Wystąpił błąd! Nie udało się pobrać wydarzeń.", Toast.LENGTH_SHORT).show();
                    if (response.code() == 401) {
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
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showEventDetails(int position) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        EventDetailsFragment detailsFragment = new EventDetailsFragment();

        Bundle arg = new Bundle();
        arg.putSerializable("event", listEvents.get(position));

        detailsFragment.setArguments(arg);
        ft.replace(R.id.fragment_container, detailsFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
