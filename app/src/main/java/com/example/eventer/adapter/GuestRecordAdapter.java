package com.example.eventer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eventer.R;
import com.example.eventer.model.GuestModel;

import java.util.ArrayList;

public class GuestRecordAdapter extends ArrayAdapter<GuestModel> {

    private static final String TAG = "GuestRecordAdapter";

    private Context con;
    int res;

    public GuestRecordAdapter(Context context, int resource, ArrayList<GuestModel> objects) {
        super(context, resource, objects);
        con = context;
        res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getFullname();
        int index = position+1;

        LayoutInflater inflater = LayoutInflater.from(con);
        convertView = inflater.inflate(res, parent, false);

        TextView guestName = convertView.findViewById(R.id.textGuestName);

        guestName.setText(index + ". " + name);

        return convertView;
    }
}
