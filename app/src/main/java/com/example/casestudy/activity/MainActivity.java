package com.example.casestudy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.casestudy.R;
import com.example.casestudy.task.FetchedDataTask;
import com.example.casestudy.model.User;
import com.example.casestudy.adapters.UserListAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCrate: Started.");

        FetchedDataTask task = new FetchedDataTask(new FetchedDataTask.TaskComplete() {
            @Override
            public void onComplete(ArrayList users) {
                initListView(users);
            }
        });

        task.execute();
    }

    private void initListView(final ArrayList listOfUsers) {

        list_view = findViewById(R.id.list_view);
        final UserListAdapter adapter = new UserListAdapter(MainActivity.this, R.layout.custom_layout, listOfUsers);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int user_id = ((User) (listOfUsers.get(position))).getId();
                System.out.println("You choose user number : " + user_id);
                Intent intent = new Intent(MainActivity.this, UserMessagesActivity.class);
                intent.putExtra("User", user_id);
                startActivity(intent);
            }
        });
    }
}
