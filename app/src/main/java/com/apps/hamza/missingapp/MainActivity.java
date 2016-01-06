package com.apps.hamza.missingapp;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    EditText  mTaskInput;
    ListView  mListView;
    TaskAdapter mAdapter;
    ProgressBar progressBar;
    Integer count =1;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(10);
        progressBar.setProgress(4);
        progressBar.getIndeterminateDrawable().setColorFilter((Color.parseColor("#a281d756")), PorterDuff.Mode.MULTIPLY);

         mTaskInput = (EditText) findViewById(R.id.task_input);
        Typeface font = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        mTaskInput.setTypeface(font);
         mListView = (ListView) findViewById(R.id.list);
         mAdapter = new TaskAdapter(this, new ArrayList<Todo>());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Todo task =mAdapter.getItem(position);
        TextView taskDescription = (TextView) view.findViewById(R.id.task_description);

        task.setState(!task.isState());

        if(task.isState()){

            taskDescription.setPaintFlags(taskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }else{
            taskDescription.setPaintFlags(taskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        //Put
        new HttpPutTask().execute(task);
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
        protected void onPreExecute() {
    progressBar.setVisibility(ProgressBar.VISIBLE);
        }
        @Override
        protected void onPostExecute(List<Todo> todos) {

            progressBar.setVisibility(ProgressBar.INVISIBLE);

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

                    return null;
                } catch (Exception e) {
                    Log.e("MainActivity", e.getMessage(), e);
                }

                return null;
            }
            @Override
            protected void onPostExecute(List<Todo> todos) {

                new HttpRequestTask().execute();

            }
            }

    private class HttpPutTask extends AsyncTask<Todo, Void, List<Todo>> {
        @Override
        protected List<Todo> doInBackground(Todo... todos) {
            try {


                //Post
                final String url = "https://nameless-inlet-8712.herokuapp.com/resources/todos/"+todos[0].getId();
                // Set the Content-Type header
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(new MediaType("application","json"));
                HttpEntity<Todo> requestEntity = new HttpEntity<Todo>(todos[0], requestHeaders);
                 Log.i("todo:",todos[0].getTask());
                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                ResponseEntity<Todo> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Todo.class);
                Todo result = responseEntity.getBody();


                return null;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        @Override
        protected void onPostExecute(List<Todo> todos) {

            new HttpRequestTask().execute();

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            new HttpRequestTask().execute();
        }

        return super.onOptionsItemSelected(item);
    }
}
