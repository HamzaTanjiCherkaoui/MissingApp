package com.apps.hamza.missingapp;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    EditText  mTaskInput;
    ListView  mListView;
    TaskAdapter mAdapter;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mTaskInput = (EditText) findViewById(R.id.task_input);
         mListView = (ListView) findViewById(R.id.list);
         mAdapter = new TaskAdapter(this, new ArrayList<Todo>());
        mListView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void createTask(View v)
    {
        if (mTaskInput.getText().length() > 0){

        new HttpPostTask().execute();
        }
    }

    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }
    private class HttpRequestTask extends AsyncTask<Void, Void, List<Todo>> {
        @Override
        protected List<Todo> doInBackground(Void... params) {
            try {
                final String url = "https://nameless-inlet-8712.herokuapp.com/resources/todos";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Todo[] todos = restTemplate.getForObject(url, Todo[].class);

                List<Todo> tds = Arrays.asList(todos);
                return tds;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Todo> todos) {


            Log.i("address 1 : ", todos.get(1).getTask());
            if (todos != null) {
                mAdapter.clear();
                mAdapter.addAll(todos);
            }

        }
    }

        private class HttpPostTask extends AsyncTask<Void, Void, List<Todo>> {
            @Override
            protected List<Todo> doInBackground(Void... params) {
                try {
                    Todo t = new Todo();
                    t.setTask(mTaskInput.getText().toString());
                    t.setState(false);
                    //Post
                    final String url = "https://nameless-inlet-8712.herokuapp.com/resources/todos";
                    // Set the Content-Type header
                    HttpHeaders requestHeaders = new HttpHeaders();
                    requestHeaders.setContentType(new MediaType("application","json"));
                    HttpEntity<Todo> requestEntity = new HttpEntity<Todo>(t, requestHeaders);

                    // Create a new RestTemplate instance
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                    ResponseEntity<Todo> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Todo.class);
                    Todo result = responseEntity.getBody();

                    Log.i("response : " , result.getTask());
                    mTaskInput.setText("");
                    return null;
                } catch (Exception e) {
                    Log.e("MainActivity", e.getMessage(), e);
                }

                return null;
            }
            @Override
            protected void onPostExecute(List<Todo> todos) {

            Log.i("post:","posting");

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
