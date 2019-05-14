package com.example.eventer.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.eventer.R;
import com.example.eventer.custom.CustomEvent;

import java.util.Calendar;

public class EventDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    CustomEvent eventD;
    TextView eventTitle;
    EditText eventDesc;
    Button btnDate, btnTime, btnEdit, btnSave;
    int day, month, year, hour, minute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventD = (CustomEvent) getArguments().getSerializable("event");

        eventTitle = getActivity().findViewById(R.id.eventTitle);
        eventDesc = getActivity().findViewById(R.id.eventDesc);
        btnDate = getActivity().findViewById(R.id.eventStartDate);
        btnTime = getActivity().findViewById(R.id.eventStartTime);
        btnEdit = getActivity().findViewById(R.id.btnEdit);
        btnSave = getActivity().findViewById(R.id.btnSave);

        eventTitle.setText(eventD.getEventTitle());
        eventDesc.setText(eventD.getEventDesc());

        btnDate.setText(eventD.getEventStartDate().substring(0, eventD.getEventStartDate().indexOf(' ')));
        btnTime.setText(eventD.getEventStartDate().substring(eventD.getEventStartDate().indexOf(' ')));


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventTitle.setEnabled(true);
                eventDesc.setEnabled(true);
                btnDate.setEnabled(true);
                btnTime.setEnabled(true);

            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), EventDetailsFragment.this,
                        year, month, day);
                datePickerDialog.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.YEAR);
                minute = c.get(Calendar.MONTH);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), EventDetailsFragment.this,
                        hour, minute, true);
                timePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String tmp = String.format("%d/%d/%d", dayOfMonth, month+1, year);
        btnDate.setText(tmp);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String hourText, minuteText;
        if (hourOfDay < 10) hourText = String.format("0%d", hourOfDay);
        else hourText = String.format("%d", hourOfDay);
        if (minute < 10) minuteText = String.format("0%d", minute);
        else minuteText = String.format("%d", minute);

        String tmp = hourText + ":" + minuteText;
        btnTime.setText(tmp);
    }
}
