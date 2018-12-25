package com.example.casestudy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.casestudy.R;
import com.example.casestudy.model.Message;

import java.util.ArrayList;

public class MassageListAdapter extends ArrayAdapter<Message> {
    private static final String TAG = "MassageListAdapter";
    private Context myContext;
    int myResource;

    public MassageListAdapter(Context context, int resource, ArrayList<Message> objects) {
        super(context, resource, objects);
        myContext = context;
        myResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String body,title;

        body = getItem(position).getMassage_body();
        title = getItem(position).getMassage_title();


        LayoutInflater layoutInflater = LayoutInflater.from(myContext);
        convertView = layoutInflater.inflate(myResource, parent, false);

        TextView textView_body = (TextView) convertView.findViewById(R.id.body_text);
        TextView textView_title = (TextView) convertView.findViewById(R.id.title_text);

        textView_body.setText(body);
        textView_title.setText(title);

        return convertView;
    }



}
