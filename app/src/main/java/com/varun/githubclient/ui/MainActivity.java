package com.varun.githubclient.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.varun.githubclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The URL to which the request is to be sent

        String url = "https://api.github.com/users/varun508/followers";


        // Creates a new Task
        MyBackgroundTask task = new MyBackgroundTask();

        /*
            This method spawns a new thread and call doInBackground function
         */
        task.execute(url);
    }


    // This class creates a new thread in background
    static class MyBackgroundTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            // gets the url which was passed in the execute method
            String url = strings[0];
            Log.d(TAG, "doInBackground: got url " + url);


            try {
                Log.d(TAG, "doInBackground: in try block");
                // Creates a new instance of the okhttp class
                OkHttpClient client = new OkHttpClient.Builder().build();
                Log.d(TAG, "doInBackground: created client");

                // builds the request
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Log.d(TAG, "doInBackground: request built");

                // send request and wait for the response
                Response response = client.newCall(request).execute();

                Log.d(TAG, "doInBackground: waiting for response");
                // send the response back
                return response.body().string();//mc jab net bnd karega to call kse hogi//run kr bsdk

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s == null) return;

            try {
                JSONArray users = new JSONArray(s);
                for(int i=0;i<users.length(); i++){
                    JSONObject user = (JSONObject) users.get(i);
                    Log.d(TAG, "onPostExecute: " + user.get("login"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // show response
            Log.d(TAG, "onPostExecute: " + s);
        }
    }
}
