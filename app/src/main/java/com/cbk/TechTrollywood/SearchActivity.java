package com.cbk.TechTrollywood;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private final static String BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0.json?apikey=";
    private final static String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private EditText searchBox;
    private AsyncHttpClient client;
    private Button searchButton;
    private ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        client = new AsyncHttpClient();
        searchButton = (Button) findViewById(R.id.search_button);
        searchBox = (EditText) findViewById(R.id.search_box);
        mArrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item_textview,new ArrayList<String>());
        ListView movieList = (ListView) findViewById(R.id.movies_search_listView);
        movieList.setAdapter(mArrayAdapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovies();
            }
        });

    }


    public void searchMovies() {
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("http").authority("api.rottentomatoes.com").path("api/public/v1.0/movies.json")
                .appendQueryParameter("apikey", API_KEY).appendQueryParameter("q", searchBox.getText().toString())
                .appendQueryParameter("page_limit", "20");
        String url = uri.build().toString();
        //Log.d("TAG", url);
        client.get(url.toString(), null, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONArray jMovies = response.getJSONArray("movies");
                    mArrayAdapter.clear();
                    for (int i = 0; i < jMovies.length(); i++) {
                        JSONObject movie = jMovies.getJSONObject(i);
                        String title = movie.getString("title");
                        mArrayAdapter.add(title);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("TAG", e.toString());
                }

            }

        });
        //Log.d("TAG", "end");
    }

}
