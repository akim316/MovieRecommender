package com.cbk.TechTrollywood;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DVDActivity extends AppCompatActivity {
    private AsyncHttpClient client;
    private ArrayAdapter<String> mArrayAdapter;
    private final static String API_KEY = "yedukp76ffytfuy24zsqk7f5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd);
        client = new AsyncHttpClient();
        mArrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item_textview,new ArrayList<String>());
        ListView recentMovies=(ListView)findViewById(R.id.dvd_listview);
        recentMovies.setAdapter(mArrayAdapter);
        getRecent();

    }
    private void getRecent(){
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("http").authority("api.rottentomatoes.com").path("api/public/v1.0/lists/dvds/new_releases.json")
                .appendQueryParameter("apikey", API_KEY);
        String url = uri.build().toString();
        Log.d("TAG", url);
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
        Log.d("TAG", "end");
    }
}
