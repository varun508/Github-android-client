package com.varun.githubclient.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.varun.githubclient.R;

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
        String url = "https://api.github.com/users/varun508";

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

            // Creates a new instance of the okhttp class
            OkHttpClient client = new OkHttpClient();

            // builds the request
            Request request = new Request.Builder()
                    .url(url)
                    .build();


            try {
                // send request and wait for the response
                Response response = client.newCall(request).execute();
                // send the response back
                return response.body().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // show response
            Log.d(TAG, "doInBackground: " + s);
        }
    }
}
