package com.cbk.TechTrollywood;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {

    private final static String URL = "http://api.rottentomatoes.com/api/public/v1.0.json?apikey=";
    private final static String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private ArrayList<String> movies = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Button searchButton = (Button) findViewById(R.id.search_button);
        final EditText searchBox = (EditText) findViewById(R.id.search_box);
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this, R.layout.content_main, movies);
        ListView movieList = (ListView) findViewById(R.id.listView);
        movieList.setAdapter(mArrayAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder uri = new Uri.Builder();
                uri.scheme("http").authority("api.rottentomatoes.com").path("api/public/v1.0/movies.json")
                        .appendQueryParameter("apikey", API_KEY).appendQueryParameter("q", searchBox.getText().toString())
                        .appendQueryParameter("page_limit", "20");
                String url = uri.build().toString();
                Log.d("SearchActivity", url);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                               //.setText("Response: " + response.toString());
                                try {

                                    JSONArray jMovies = response.getJSONArray("movies");
                                    for (int i = 0; i < jMovies.length(); i++) {
                                        JSONObject movie = jMovies.getJSONObject(i);
                                        String title = movie.getString("title");
                                        movies.add(title);
                                    }
                                    Log.d(this.getClass().getSimpleName(), Arrays.toString(movies.toArray()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.d(this.getClass().getSimpleName(), "something went wrong");
                            }
                        });
            }
        });
    }

//    public void searchMovies() {
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        mTxtDisplay.setText("Response: " + response.toString());
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//
//                    }
//                });
//
//    }

}
