package com.example.casestudy.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.example.casestudy.R;
import com.example.casestudy.adapters.MassageListAdapter;
import com.example.casestudy.task.FetchedUserMessageTask;
import java.util.ArrayList;

public class UserMessagesActivity extends AppCompatActivity {

    private static final String TAG = "UserMessagesActivity";
    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_massages);
        Log.d(TAG, "onCrate: Started.");

        Bundle extras = getIntent().getExtras();
        FetchedUserMessageTask parsing_process = new FetchedUserMessageTask(new FetchedUserMessageTask.TaskComplete() {
            @Override
            public void onComplete(ArrayList listOfMessages) {
                initListView(listOfMessages);
            }
        },extras);
        parsing_process.execute();
    }

    private void initListView(final ArrayList listOfMassages) {
        Log.d(TAG,"initListView Started");
        list_view = findViewById(R.id.massage_list_view);
        final MassageListAdapter adapter = new MassageListAdapter(UserMessagesActivity.this, R.layout.massage_custom_layout, listOfMassages);
        list_view.setAdapter(adapter);

    }

}
