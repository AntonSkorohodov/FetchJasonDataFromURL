package com.example.anton.urlfetchdata;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class MassageListAdapter extends ArrayAdapter<Massage> {
    private static final String TAG = "MassageListAdapter";
    private Context myContext;
    int myResource;

    public MassageListAdapter(Context context, int resource, ArrayList<Massage> objects) {
        super(context, resource, objects);
        myContext = context;
        myResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String body,title;

        body = getItem(position).getMassage_body();
        title = getItem(position).getMassage_title();

       /* body = "hay";
        title = "kaboom";*/
        LayoutInflater layoutInflater = LayoutInflater.from(myContext);
        convertView = layoutInflater.inflate(myResource, parent, false);

        TextView textView_body = (TextView) convertView.findViewById(R.id.body_text);
        TextView textView_title = (TextView) convertView.findViewById(R.id.title_text);

        textView_body.setText(body);
        textView_title.setText(title);

        Log.d(TAG,"////////////////Final Massage//////////////////");


        return convertView;
    }



}
