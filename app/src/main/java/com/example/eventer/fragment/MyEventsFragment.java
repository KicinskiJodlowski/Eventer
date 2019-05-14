package com.example.eventer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eventer.R;
import com.example.eventer.adapter.CustomAdapter;
import com.example.eventer.custom.CustomEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyEventsFragment extends Fragment {

    ListView listViewEvents;

    public static ArrayList<CustomEvent> listEvents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_events_fragment, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewEvents = getActivity().findViewById(R.id.listViewEvents);

        CustomEvent event1 = new CustomEvent(1,"MegaWAT", "10/05/2019 17:00",
                "Najlepsze juwenalia w Warszawie, dwa dni, dziesięć zezpołów, ponad 15 tys uczestnikow", 1);

        CustomEvent event2 = new CustomEvent(2, "Piknik WCY", "11/06/2019 15:00",
                "Chlanie piwa, wódki itp itd", 2);

        listEvents = new ArrayList<>();
        listEvents.add(event1);
        listEvents.add(event2);

        CustomAdapter adapter = new CustomAdapter(getActivity(), R.layout.event_record, listEvents);
        listViewEvents.setAdapter(adapter);
;


    }
}
