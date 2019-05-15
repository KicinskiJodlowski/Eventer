package com.example.eventer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eventer.R;
import com.example.eventer.custom.CustomEvent;
import com.example.eventer.fragment.MyEventsFragment;
import com.example.eventer.model.EventModel;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<EventModel> {

    private static final String TAG = "CustomAdapter";

    private Context con;
    int res;

    public CustomAdapter(Context context, int resource, ArrayList<EventModel> objects) {
        super(context, resource, objects);
        con = context;
        res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String title = getItem(position).getEventName();
        String date = getItem(position).getDateOfEvent();
        String desc = getItem(position).getDescription();

        //CustomEvent event = new CustomEvent(title,date,desc);

        LayoutInflater inflater = LayoutInflater.from(con);
        convertView = inflater.inflate(res, parent, false);

        TextView eventTitle = convertView.findViewById(R.id.textEventTitle);
        TextView eventDate = convertView.findViewById(R.id.textEventDate);
        TextView eventDesc = convertView.findViewById(R.id.textEventDesc);

        eventTitle.setText(title);
        eventDate.setText(date.substring(0, date.indexOf("T")) + " " + date.substring(date.indexOf("T")+1));
        if( desc.length() > 75)
            eventDesc.setText(desc.substring(0,75)+"...");
        else
            eventDesc.setText(desc);

        return convertView;
    }
}
