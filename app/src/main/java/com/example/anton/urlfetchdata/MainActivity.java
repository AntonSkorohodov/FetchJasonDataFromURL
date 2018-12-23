package com.example.anton.urlfetchdata;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String CUA = "CustomAdapter";
    private static final String FETCHEDDATA = "FetchedData";
    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCrate: Started.");

        list_view = (ListView) findViewById(R.id.list_view);
        fetchedData process = new fetchedData();
        process.execute();
    }


    class fetchedData extends AsyncTask<Void,Void,Void> {

    String data = "";
    JsonArray jsonArray;
    ArrayList userList;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Log.d(FETCHEDDATA,"doInBackground Started.");
            URL url = new URL("https://jsonplaceholder.typicode.com/users");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while(line != null){
                data = data + line;
                line = bufferedReader.readLine();
            }
            JsonElement jelement = new JsonParser().parse(data);
            System.out.println(jelement.toString());
            jsonArray = jelement.getAsJsonArray();

            Log.d(FETCHEDDATA,"Before Creating Users ArrayList");
            userList = new ArrayList();
            for(int i = 0 ;i<jsonArray.size();i++){
                User local = new User(getId(i,jsonArray),getName(i,jsonArray),getUsername(i,jsonArray),getEmail(i,jsonArray),getAddress(i,jsonArray));
                userList.add(local);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public int getId(int index, JsonArray jsonArray) {
        return jsonArray.get(index).getAsJsonObject().get("id").getAsInt();
    }

    public String getName(int index, JsonArray jsonArray) {
        return jsonArray.get(index).getAsJsonObject().get("name").getAsString();
    }

    public String getUsername(int index, JsonArray jsonArray) {
        return jsonArray.get(index).getAsJsonObject().get("username").getAsString();
    }

    public String getEmail(int index, JsonArray jsonArray) {
        return jsonArray.get(index).getAsJsonObject().get("email").getAsString();
    }

    public Address getAddress(int index,JsonArray jsonArray) {
        return new Address(jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("street").getAsString(),
                jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("suite").getAsString(),
                jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("city").getAsString(),
                jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("zipcode").getAsString(),
                getGeo(index,jsonArray));
    }

    public Geo getGeo(int index, JsonArray jsonArray){
        return new Geo(jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("geo").getAsJsonObject().get("lat").getAsFloat(),
                jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("geo").getAsJsonObject().get("lng").getAsFloat());
    }

    public ArrayList<User> getUserList(){
        return userList;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        list_view = (ListView) findViewById(R.id.list_view);
        final UserListAdapter adapter = new UserListAdapter(MainActivity.this,R.layout.custom_layout,userList);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int user_id =((User)(userList.get(position))).getId();
                System.out.println("You choose user number : "+user_id);
                Intent intent = new Intent(MainActivity.this,UserMassagesActivity.class);
                intent.putExtra("User",user_id);
                startActivity(intent);

            }
        });
    }

}
}
