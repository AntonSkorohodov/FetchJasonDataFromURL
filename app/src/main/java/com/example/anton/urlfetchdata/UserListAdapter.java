package com.example.anton.urlfetchdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class UserListAdapter extends ArrayAdapter<User> {
    private static final String TAG = "UserListAdapter";
    private Context mContext;
    int mResource;

    public UserListAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name, username, email, address;

        name = getItem(position).getName();
        username = getItem(position).getUsername();
        email = getItem(position).getEmail();
        address = getItem(position).getAddress();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView textView_name = (TextView) convertView.findViewById(R.id.name_text);
        TextView textView_username = (TextView) convertView.findViewById(R.id.username_text);
        TextView textView_email = (TextView) convertView.findViewById(R.id.email_text);
        TextView textView_address = (TextView) convertView.findViewById(R.id.address_text);

        textView_name.setText(name);
        textView_username.setText(username);
        textView_email.setText(email);
        textView_address.setText(address);

        return convertView;
    }
}
