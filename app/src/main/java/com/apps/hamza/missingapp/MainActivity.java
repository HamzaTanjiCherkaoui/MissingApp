package com.apps.hamza.missingapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }
    private class HttpRequestTask extends AsyncTask<Void, Void, MissingPerson[]> {
        @Override
        protected MissingPerson[] doInBackground(Void... params) {
            try {
                final String url = "https://nameless-inlet-8712.herokuapp.com/resources/missingpeoples";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                MissingPerson[] mps = restTemplate.getForObject(url, MissingPerson[].class);
                Log.i("objee",mps[0].getLastName());
                return mps;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        @Override
        protected void onPostExecute(MissingPerson[] mps) {

            TextView greetingContentText = (TextView) findViewById(R.id.hellorest);

            Log.i("object",mps[1].getFirstName());
            greetingContentText.setText(mps[1].getFirstName());
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
