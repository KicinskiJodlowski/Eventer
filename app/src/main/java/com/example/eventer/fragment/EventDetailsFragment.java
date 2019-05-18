package com.example.eventer.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventer.R;
import com.example.eventer.RetrofitClient;
import com.example.eventer.SharedPreferenceManager;
import com.example.eventer.adapter.EventRecordAdapter;
import com.example.eventer.adapter.GuestRecordAdapter;
import com.example.eventer.model.EventModel;
import com.example.eventer.model.GuestModel;
import com.example.eventer.model.InvitedGuest;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    ListView listViewGuests;
    public static ArrayList<GuestModel> listGuests;
    EditText eventTitle, eventDesc;
    Button btnDate, btnTime, btnEdit, btnSave, btnShare, btnShow, btnHide;
    int day, month, year, hour, minute;
    ImageView imageEvent;
    String imageBase64;
    Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventD = (EventModel) getArguments().getSerializable("event");

        imageEvent = getActivity().findViewById(R.id.imageEvent);
        eventTitle = getActivity().findViewById(R.id.eventTitle);
        eventDesc = getActivity().findViewById(R.id.eventDesc);
        btnDate = getActivity().findViewById(R.id.eventStartDate);
        btnTime = getActivity().findViewById(R.id.eventStartTime);
        btnEdit = getActivity().findViewById(R.id.btnEdit);
        btnSave = getActivity().findViewById(R.id.btnSave);
        btnShare = getActivity().findViewById(R.id.btnShare);
        btnShow = getActivity().findViewById(R.id.btnShowGuests);
        btnHide = getActivity().findViewById(R.id.btnHideGuests);
        listViewGuests = getActivity().findViewById(R.id.listGuests);

        eventTitle.setText(eventD.getEventName());
        eventDesc.setText(eventD.getDescription());
        btnDate.setText(eventD.getDateOfEvent().substring(0, eventD.getDateOfEvent().indexOf('T')));
        btnTime.setText(eventD.getDateOfEvent().substring(eventD.getDateOfEvent().indexOf('T')+1));

        if(eventD.getImgURL() != null)
        {
            imageBase64 = eventD.getImgURL();
            bitmap = convertToImage();
            imageEvent.setImageBitmap(bitmap);
        }

        if(eventD.getOwnerID().contentEquals(SharedPreferenceManager.read(SharedPreferenceManager.ID,"")))
        {
            btnEdit.setVisibility(View.VISIBLE);
        }

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnShow.setVisibility(View.GONE);
                btnHide.setVisibility(View.VISIBLE);
                listViewGuests.setVisibility(View.VISIBLE);

                listGuests = new ArrayList<>();
                getGuests();
            }
        });

        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewGuests.setVisibility(View.GONE);
                btnHide.setVisibility(View.GONE);
                btnShow.setVisibility(View.VISIBLE);

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventTitle.setEnabled(true);
                eventDesc.setEnabled(true);
                btnDate.setEnabled(true);
                btnTime.setEnabled(true);

                listViewGuests.setVisibility(View.GONE);
                btnShare.setVisibility(View.GONE);
                btnShow.setVisibility(View.GONE);
                btnHide.setVisibility(View.GONE);
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

                btnShare.setVisibility(View.VISIBLE);
                btnShow.setVisibility(View.VISIBLE);
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

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                TextToQrCode(eventD.getEventQRCode());
            }
        });
    }

    private void getGuests() {

        Call<ArrayList<GuestModel>> call = RetrofitClient.getInstance().getApi().getGuests(eventD.getEventId(),SharedPreferenceManager.read(SharedPreferenceManager.TOKEN,""));

        call.enqueue(new Callback<ArrayList<GuestModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GuestModel>> call, Response<ArrayList<GuestModel>> response) {

                Log.d("Response Code: ", Integer.toString(response.code()));

                Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();

                if(response.code() == 200) {

                    Toast.makeText(getActivity(), "Pobrano uczestników", Toast.LENGTH_SHORT).show();

                    GuestRecordAdapter adapter = new GuestRecordAdapter(getActivity(), R.layout.guest_record, listGuests);
                    listViewGuests.setAdapter(adapter);
                }

                else {
                    Toast.makeText(getActivity(), "Wystąpił błąd! Nie udało się pobrać uczestników.", Toast.LENGTH_SHORT).show();
                }


                ArrayList<GuestModel> guests = response.body();

                for(GuestModel guest : guests)
                {
                    //if(event.getEventName() == null) event.setEventName("BRAK NAZWY"); // to prevent error with events with no title
                    listGuests.add(guest); //tested - OK
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GuestModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Bitmap convertToImage() {

        byte[] imageBytes = Base64.decode(imageBase64, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return  decodedImage;
    }

    private void updateEvent()
    {
        Integer eventId;
        String title, date, desc, imgURL, ownerId, qrCode, jobIdScheduler;
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
        jsonParams.put("jobIDScheduler", jobIdScheduler);
        jsonParams.put("ownerID", ownerId);
        jsonParams.put("invitedGuests", guests);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateEvent(eventD.getEventId(), LoginFragment.userTOKEN, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("Response code: ", Integer.toString(response.code()));

                if(response.code() == 204) {
                    Toast.makeText(getActivity(),"Zmiany zostały zapisane",Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyEventsFragment()).commit();
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

    private void TextToQrCode(String Value) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap("content", BarcodeFormat.QR_CODE, 400, 400);
            ImageView imageViewQrCode = (ImageView) getActivity().findViewById(R.id.qrCode);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch(Exception e) {
        }
    }

}
