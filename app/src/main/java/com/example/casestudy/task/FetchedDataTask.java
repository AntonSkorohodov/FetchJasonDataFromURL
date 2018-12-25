package com.example.casestudy.task;

import android.os.AsyncTask;
import android.util.Log;
import com.example.casestudy.model.Address;
import com.example.casestudy.model.Geo;
import com.example.casestudy.model.User;
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

public class FetchedDataTask extends AsyncTask<Void, Void, Void> {

    private static final String FETCHEDDATA = "FetchedDataTask";

    private TaskComplete onComplete;
    String data = "";
    JsonArray jsonArray;
    ArrayList userList;


    public interface TaskComplete {
        void onComplete(ArrayList users);
    }

    public FetchedDataTask(final TaskComplete taskComplete) {
        this.onComplete = taskComplete;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Log.d(FETCHEDDATA, "doInBackground Started.");
            URL url = new URL("https://jsonplaceholder.typicode.com/users");

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

            Log.d(FETCHEDDATA, "Before Creating Users ArrayList");
            userList = new ArrayList();
            for (int i = 0; i < jsonArray.size(); i++) {
                User local = new User(getId(i, jsonArray), getName(i, jsonArray), getUsername(i, jsonArray), getEmail(i, jsonArray), getAddress(i, jsonArray));
                userList.add(local);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getId(int index, JsonArray jsonArray) {
        return jsonArray.get(index).getAsJsonObject().get("id").getAsInt();
    }

    private String getName(int index, JsonArray jsonArray) {
        return jsonArray.get(index).getAsJsonObject().get("name").getAsString();
    }

    private String getUsername(int index, JsonArray jsonArray) {
        return jsonArray.get(index).getAsJsonObject().get("username").getAsString();
    }

    private String getEmail(int index, JsonArray jsonArray) {
        return jsonArray.get(index).getAsJsonObject().get("email").getAsString();
    }

    private Address getAddress(int index, JsonArray jsonArray) {
        return new Address(jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("street").getAsString(),
                jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("suite").getAsString(),
                jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("city").getAsString(),
                jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("zipcode").getAsString(),
                getGeo(index, jsonArray));
    }


    private Geo getGeo(int index, JsonArray jsonArray) {
        return new Geo(jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("geo").getAsJsonObject().get("lat").getAsFloat(),
                jsonArray.get(index).getAsJsonObject().get("address").getAsJsonObject().get("geo").getAsJsonObject().get("lng").getAsFloat());
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onComplete.onComplete(userList);
    }


}
