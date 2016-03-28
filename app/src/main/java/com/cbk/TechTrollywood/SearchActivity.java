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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    private EditText searchBox;
    private AsyncHttpClient client;
    private Button searchButton;
    private ListView listView;
    private CustomListAdapter movieListAdapter;
    private ArrayList<String> idArray;
    private List<Movie> movies = new ArrayList<Movie>();
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
        movieListAdapter = new CustomListAdapter(this, movies);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(movieListAdapter);
        idArray = new ArrayList<>();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovies();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = view.getContext();
                Intent launchDetail = new Intent(context, DetailActivity.class);
                ArrayList<String> extraData = new ArrayList<>();
                extraData.add(((Movie) movieListAdapter.getItem(position)).getTitle());
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
        mAuthProgressDialog.setCancelable(true);
        mAuthProgressDialog.show();
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("http").authority("api.rottentomatoes.com").path("api/public/v1.0/movies.json")
                .appendQueryParameter("apikey", getString(R.string.key)).appendQueryParameter("q", searchBox.getText().toString())
                .appendQueryParameter("page_limit", "20");
        String url = uri.build().toString();
        //Log.d("TAG", url);
        client.get(url.toString(), null, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONArray jMovies = response.getJSONArray("movies");
                    movies.clear();
                    idArray.clear();
                    if (jMovies.length() == 0) {
                        Toast toast = Toast.makeText(getApplicationContext(), "No movies found", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    for (int i = 0; i < jMovies.length(); i++) {
                        JSONObject movieObj = jMovies.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setTitle(movieObj.getString("title"));
                        String id = movieObj.getString("id");
                        movie.setId(Integer.valueOf(id));
                        String year = movieObj.getString("year");
                        if (!year.equals("")) {
                            movie.setYear(Integer.valueOf(year));
                        }
                        JSONObject ratings = movieObj.getJSONObject("ratings");
                        movie.setRating(Integer.valueOf(ratings.getString("audience_score")));
                        String critics = ratings.getString("critics_score");
                        movie.setRating2(Integer.valueOf(critics));

                        JSONObject posters = movieObj.getJSONObject("posters");
                        movie.setThumbnailUrl(posters.getString("thumbnail"));
                        movies.add(movie);
                        idArray.add(id);
                    }
                    mAuthProgressDialog.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("TAG", e.toString());
                }
                movieListAdapter.notifyDataSetChanged();
            }
            public void onFailure(Throwable e) {
                Log.e("TAG", "OnFailure!", e);
                mAuthProgressDialog.hide();
            }

        });
        //Log.d("TAG", "end");
    }

}
