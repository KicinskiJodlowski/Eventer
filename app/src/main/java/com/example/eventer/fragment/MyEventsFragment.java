package com.example.eventer.fragment;

import android.os.Bundle;
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
import com.example.eventer.adapter.EventRecordAdapter;
import com.example.eventer.model.EventModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Call<ArrayList<EventModel>> call = RetrofitClient.getInstance().getApi().getEvents(SharedPreferenceManager.read(SharedPreferenceManager.TOKEN,""));

        call.enqueue(new Callback<ArrayList<EventModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EventModel>> call, Response<ArrayList<EventModel>> response) {

                Log.d("Response Code: ", Integer.toString(response.code()));

                Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();

                if(response.code() == 200) {

                    Toast.makeText(getActivity(), "Pobrano wydarzenia", Toast.LENGTH_SHORT).show();

                    EventRecordAdapter adapter = new EventRecordAdapter(getActivity(), R.layout.event_record, listEvents);
                    listViewEvents.setAdapter(adapter);
                    listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            showEventDetails(position);
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Wystąpił błąd! Nie udało się pobrać wydarzeń.", Toast.LENGTH_SHORT).show();
                }


                ArrayList<EventModel> events = response.body();

                for(EventModel event : events)
                {
                    //if(event.getEventName() == null) event.setEventName("BRAK NAZWY"); // to prevent error with events with no title
                    listEvents.add(event); //tested - OK
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showEventDetails(int position) {
        FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
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
