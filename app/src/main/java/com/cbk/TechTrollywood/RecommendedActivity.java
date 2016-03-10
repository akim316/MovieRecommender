package com.cbk.TechTrollywood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private String myMajor;
    private AuthData authData;
    Boolean movieAdded;
    ArrayAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);
        fb = new Firebase(getResources().getString(R.string.firebase_url));
        mArrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.list_item_textview, new ArrayList<String>());
        ListView recentMovies = (ListView) findViewById(R.id.recommended_listView);
        recentMovies.setAdapter(mArrayAdapter);
        getRecommendations();
    }

    public void getRecommendations() {
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
                            Object mym=snapshot.child("users").child(authData.getUid()).child("Major").getValue();
//                            if(m!=null){
//                                Log.d("TAG","major");
//                                Log.d("TAG",m.toString());
//                            }
//                            if(mym!=null){
//                                Log.d("TAG","mymajor");
//                                Log.d("TAG", mym.toString());
//                            }
//                            Log.d("TAG",s);
                            if(m!=null && mym!=null) {
                                major = m.toString();
                                myMajor = mym.toString();
//                                Log.d("TAG",major);
//                                Log.d("TAG", myMajor);
                                if (rating > 3 && major.equalsIgnoreCase(myMajor) && !movieAdded) {
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
