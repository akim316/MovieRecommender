package com.cbk.TechTrollywood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    private ArrayList<String> idArray;
    /* A dialog that is presented until the Firebase authentication finished. */
    private ProgressDialog mAuthProgressDialog;

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
        idArray =new ArrayList<>();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovies();
            }
        });

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = view.getContext();
                Intent launchDetail = new Intent(context, DetailActivity.class);
                ArrayList<String> extraData=new ArrayList<>();
                extraData.add(mArrayAdapter.getItem(position));
                extraData.add(idArray.get(position));
                launchDetail.putStringArrayListExtra("extra", (ArrayList<String>) extraData);
                startActivity(launchDetail);
            }
        });

    }


    public void searchMovies() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Please wait");
        mAuthProgressDialog.setMessage("Searching...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();
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
                    idArray.clear();
                    for (int i = 0; i < jMovies.length(); i++) {
                        JSONObject movie = jMovies.getJSONObject(i);
                        String title = movie.getString("title");
                        mArrayAdapter.add(title);
                        String id = movie.getString("id");
                        idArray.add(id);
                        //Log.d("TAG",idArray.toString());
                    }
                    mAuthProgressDialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("TAG", e.toString());
                }

            }
            public void onFailure(Throwable e) {
                Log.e("TAG", "OnFailure!", e);
                mAuthProgressDialog.hide();
            }

        });
        //Log.d("TAG", "end");
    }

}
