package com.cbk.TechTrollywood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * activity for showing recent DVDs
 */
public class DVDActivity extends AppCompatActivity {
    private AsyncHttpClient client;
    private ArrayAdapter<String> mArrayAdapter;
    private List<String> idArray;
    private List<String> synArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd);
        client = new AsyncHttpClient();
        mArrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.list_item_textview,new ArrayList<String>());
        ListView recentMovies=(ListView)findViewById(R.id.dvd_listview);
        recentMovies.setAdapter(mArrayAdapter);
        getRecent();

        synArray=new ArrayList<>();
        idArray =new ArrayList<>();
        recentMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = view.getContext();
                Intent launchDetail = new Intent(context, DetailActivity.class);
                ArrayList<String> extraData = new ArrayList<>();
                extraData.add(mArrayAdapter.getItem(position));
                extraData.add(idArray.get(position));
                extraData.add(synArray.get(position));
                launchDetail.putStringArrayListExtra("extra", extraData);
                startActivity(launchDetail);
            }
        });

    }

    /**
     *
     */
    private void getRecent() {
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("http").authority("api.rottentomatoes.com").path("api/public/v1.0/lists/dvds/new_releases.json")
                .appendQueryParameter("apikey", getString(R.string.key));
        String url = uri.build().toString();
        Log.d("TAG", url);
        client.get(url, null, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONArray jMovies = response.getJSONArray("movies");
                    mArrayAdapter.clear();
                    idArray.clear();
                    synArray.clear();
                    for (int i = 0; i < jMovies.length(); i++) {
                        JSONObject movie = jMovies.getJSONObject(i);
                        String title = movie.getString("title");
                        String id = movie.getString("id");
                        String synopsis=movie.getString("synopsis");
                        mArrayAdapter.add(title);
                        idArray.add(id);
                        synArray.add(synopsis);
                    }
                } catch (JSONException e) {
                    Log.d("TAG", e.toString());
                }

            }

        });
        Log.d("TAG", "end");
    }
}