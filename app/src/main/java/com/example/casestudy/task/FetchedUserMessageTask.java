package com.example.casestudy.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.example.casestudy.model.Message;
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



public class FetchedUserMessageTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "FetchUserMessageTask";

    private TaskComplete onComplete;
    String data = "";
    JsonArray jsonArray;
    ArrayList listOfMassages;
    Bundle extras;

    public interface TaskComplete {
        void onComplete(ArrayList listOfMassages);
    }

    public FetchedUserMessageTask(final FetchedUserMessageTask.TaskComplete taskComplete,Bundle extras) {
        this.extras = extras;
        this.onComplete = taskComplete;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Log.d(TAG, "doInBackground Started.");
            String url_string= "https://jsonplaceholder.typicode.com/posts?userId=";
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
            listOfMassages = new ArrayList();
            for (int i = 0; i < jsonArray.size(); i++) {
                Message local = new Message(getId(i, jsonArray), getTitle(i, jsonArray), getBody(i, jsonArray));
                listOfMassages.add(local);
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

    public ArrayList<Message> getUserList() {
        return listOfMassages;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onComplete.onComplete(listOfMassages);
    }
}
