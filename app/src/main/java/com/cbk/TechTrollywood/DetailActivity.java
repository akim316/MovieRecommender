package com.cbk.TechTrollywood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private Firebase fb;
    ArrayList<String> extraData;
    String rating;
    TextView movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        movieName=(TextView)findViewById(R.id.movie_name_field);
        fb = new Firebase(getResources().getString(R.string.firebase_url));
        Intent intent = getIntent();
        if (intent != null) {
            extraData = intent.getStringArrayListExtra("extra");//[0]=movie name [1]=id
            movieName.setText(extraData.get(0));
            getRating(extraData.get(1));
            //Log.d("TAG",extraData.toString());
        }



    }
    public void onRatingClicked(View view) {
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rating_1:
                Toast.makeText(view.getContext(),
                        "You have selected 1",
                        Toast.LENGTH_SHORT).show();
                setRating(1,extraData.get(1));
                break;
            case R.id.rating_2:
                Toast.makeText(view.getContext(),
                        "You have selected 2",
                        Toast.LENGTH_SHORT).show();
                setRating(2,extraData.get(1));
                break;
            case R.id.rating_3:
                Toast.makeText(view.getContext(),
                        "You have selected 3",
                        Toast.LENGTH_SHORT).show();
                setRating(3,extraData.get(1));
                break;
            case R.id.rating_4:
                Toast.makeText(view.getContext(),
                        "You have selected 4",
                        Toast.LENGTH_SHORT).show();
                setRating(4,extraData.get(1));
                break;
            case R.id.rating_5:
                Toast.makeText(view.getContext(),
                        "You have selected 5",
                        Toast.LENGTH_SHORT).show();
                setRating(5,extraData.get(1));
                break;
        }
    }

    private void setRating(int rating, String id){
        AuthData authData = fb.getAuth();
        if (authData != null) {
            fb.child("users").child(authData.getUid()).child(id).setValue(rating);
        } else {
            Toast.makeText(getApplicationContext(), "No user authenticated",
                    Toast.LENGTH_LONG).show();
        }
    }
    private void getRating(String id){
        AuthData authData = fb.getAuth();
        if (authData != null) {
            fb.child("users").child(authData.getUid()).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {
                        rating = snapshot.getValue().toString();
                        Log.d("TAG", rating);
                        RadioButton rb;
                        switch (rating) {
                            case "1":
                                rb = (RadioButton) findViewById(R.id.rating_1);
                                rb.setChecked(true);
                                break;
                            case "2":
                                rb = (RadioButton) findViewById(R.id.rating_2);
                                rb.setChecked(true);
                                break;
                            case "3":
                                rb = (RadioButton) findViewById(R.id.rating_3);
                                rb.setChecked(true);
                                break;
                            case "4":
                                rb = (RadioButton) findViewById(R.id.rating_4);
                                rb.setChecked(true);
                                break;
                            case "5":
                                rb = (RadioButton) findViewById(R.id.rating_5);
                                rb.setChecked(true);
                                break;
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No user authenticated",
                    Toast.LENGTH_LONG).show();
        }
    }
}
