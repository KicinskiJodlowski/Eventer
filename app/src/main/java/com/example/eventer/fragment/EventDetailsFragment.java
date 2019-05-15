package com.example.eventer.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.RetrofitClient;
import com.example.eventer.model.EventModel;
import com.example.eventer.model.InvitedGuest;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EventModel eventD;

    EditText eventTitle, eventDesc;
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

        eventD = (EventModel) getArguments().getSerializable("event");

        eventTitle = getActivity().findViewById(R.id.eventTitle);
        eventDesc = getActivity().findViewById(R.id.eventDesc);
        btnDate = getActivity().findViewById(R.id.eventStartDate);
        btnTime = getActivity().findViewById(R.id.eventStartTime);
        btnEdit = getActivity().findViewById(R.id.btnEdit);
        btnSave = getActivity().findViewById(R.id.btnSave);

        eventTitle.setText(eventD.getEventName());
        eventDesc.setText(eventD.getDescription());

        btnDate.setText(eventD.getDateOfEvent().substring(0, eventD.getDateOfEvent().indexOf('T')));
        btnTime.setText(eventD.getDateOfEvent().substring(eventD.getDateOfEvent().indexOf('T')+1));

        if(eventD.getOwnerID() != null)
        {
            btnEdit.setVisibility(View.VISIBLE);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventTitle.setEnabled(true);
                eventDesc.setEnabled(true);
                btnDate.setEnabled(true);
                btnTime.setEnabled(true);

                btnEdit.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventTitle.setEnabled(false);
                eventDesc.setEnabled(false);
                btnDate.setEnabled(false);
                btnTime.setEnabled(false);

                btnEdit.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.GONE);

                for (EventModel event: MyEventsFragment.listEvents)
                {
                    if(event.getEventId() == eventD.getEventId()) {

                        updateEvent();
                    }
                }
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

    private void updateEvent()
    {
        Integer eventId;
        String title, date, desc, ownerId, qrCode, jobIdScheduler;
        Object imgURL;
        List<InvitedGuest> guests;

        eventId = eventD.getEventId();
        title = eventTitle.getText().toString().trim();
        date = btnDate.getText().toString().trim() + " " + btnTime.getText().toString().trim();
        desc = eventDesc.getText().toString().trim();
        imgURL = eventD.getImgURL();
        qrCode = eventD.getEventQRCode();
        jobIdScheduler = eventD.getJobIDscheduler();
        ownerId = eventD.getOwnerID();
        guests = eventD.getInvitedGuests();

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("eventId", eventId);
        jsonParams.put("eventName", title);
        jsonParams.put("dateOfEvent", date);
        jsonParams.put("description", desc);
        jsonParams.put("imgURL", imgURL);
        jsonParams.put("eventQRCode", qrCode);
        jsonParams.put("jobIDscheduler", jobIdScheduler);
        jsonParams.put("ownerID", ownerId);
        jsonParams.put("invitedGuests", guests);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateEvent(eventD.getEventId(), LoginFragment.userTOKEN, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("RESPONSE CODE --> ", Integer.toString(response.code()));

                String s = null;

                if(response.code() == 204) {
                    Toast.makeText(getActivity(),"Zmiany zostały zapisane",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Wystąpił błąd! Upewnij się, że wprowadzono nazwę oraz wybrano datę", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String validMonth, validDay;
        month += 1;
        if (month < 10) validMonth = String.format("0%d", month);
        else validMonth = String.format("%d", month);
        if (dayOfMonth < 10) validDay = String.format("0%d", dayOfMonth);
        else validDay = String.format("%d", dayOfMonth);

        String tmp = String.format("%d/", year) + validMonth + "/" + validDay;
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
