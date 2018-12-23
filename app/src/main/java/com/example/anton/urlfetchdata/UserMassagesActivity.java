package com.example.anton.urlfetchdata;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UserMassagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_massages);
        jsonParser parsing_process = new jsonParser();
        parsing_process.execute();
    }

    class jsonParser extends AsyncTask<Void,Void,Void> {

        private static final String TAG = "UserMassagesActivity";
        String data = "";
        JsonArray jsonArray;
        ArrayList massageList;
        ListView massageListView;


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Log.d(TAG, "doInBackground Started.");
                String url_string= "https://jsonplaceholder.typicode.com/posts?userId=";
                Bundle extras = getIntent().getExtras();
                if(extras != null){
                    url_string = url_string + extras.getInt("User");
                }

                URL url = new URL(url_string);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while (line != null) {
                    data = data + line;
                    line = bufferedReader.readLine();
                }
                JsonElement jelement = new JsonParser().parse(data);
                System.out.println(jelement.toString());
                jsonArray = jelement.getAsJsonArray();

                Log.d(TAG, "Before Creating ArrayList");
                massageList = new ArrayList();
                for (int i = 0; i < jsonArray.size(); i++) {
                    Massage local = new Massage(getId(i, jsonArray), getTitle(i, jsonArray), getBody(i, jsonArray));
                    massageList.add(local);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        public String getId(int index, JsonArray jsonArray) {
            return jsonArray.get(index).getAsJsonObject().get("id").getAsString();
        }

        public String getTitle(int index, JsonArray jsonArray) {
            return jsonArray.get(index).getAsJsonObject().get("title").getAsString();
        }

        public String getBody(int index, JsonArray jsonArray) {
            return jsonArray.get(index).getAsJsonObject().get("body").getAsString();
        }

        public ArrayList<User> getUserList() {
            return massageList;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            massageListView = (ListView) findViewById(R.id.massage_list_view);
            final MassageListAdapter adapter = new MassageListAdapter(UserMassagesActivity.this, R.layout.massage_custom_layout, massageList);
            massageListView.setAdapter(adapter);

        }
    }
}
