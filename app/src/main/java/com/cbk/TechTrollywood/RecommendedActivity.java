package com.cbk.TechTrollywood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class RecommendedActivity extends AppCompatActivity {
    private Firebase fb;
    private String movieName;
    private String movieID;
    private String userID;
    private int rating;
    private String major;
    private String searchMajor;
    private AuthData authData;
    private EditText majorfield;
    private Button searchButton;
    private RatingBar ratingbar;
    private Boolean movieAdded;
    private ArrayAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);
        fb = new Firebase(getResources().getString(R.string.firebase_url));
        mArrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.list_item_textview, new ArrayList<String>());
        ListView recentMovies = (ListView) findViewById(R.id.recommended_listView);
        majorfield=(EditText) findViewById(R.id.search_major_edittext);
        searchButton=(Button) findViewById(R.id.recommendation_search_button);
        ratingbar=(RatingBar) findViewById(R.id.search_ratingbar);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating=Math.round(ratingbar.getRating());
                String major=majorfield.getText().toString();
                getRecommendations(major,rating);
            }
        });


        recentMovies.setAdapter(mArrayAdapter);

    }

    public void getRecommendations(String searchMajorP,int searchRatingP) {
        final String searchMajor=searchMajorP;
        final int searchRating=searchRatingP;
        mArrayAdapter.clear();
        authData = fb.getAuth();
        if (authData != null) {
            fb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot movies : snapshot.child("movies").getChildren()) {
                        movieAdded = false;
                        movieName = movies.child("name").getValue().toString();
                        movieID = movies.getValue().toString();
                        //Log.d("TAG",movieName);
                        for (DataSnapshot ratings : movies.child("ratings").getChildren()) {
                            userID = ratings.getKey();
                            //Log.d("TAG",userID);
                            String s = ratings.child("rating").getValue().toString();
                            rating = Integer.parseInt(s);
                            Object m=snapshot.child("users").child(userID).child("Major").getValue();
                            if(m!=null) {
                                major = m.toString();
                                if (rating >= searchRating && major.equalsIgnoreCase(searchMajor) && !movieAdded) {
                                    //Log.d("TAG",movieName);
                                    movieAdded = true;
                                    mArrayAdapter.add(movieName);
                                }
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d("TAG", firebaseError.getMessage());
                }
            });
        }
    }
}
