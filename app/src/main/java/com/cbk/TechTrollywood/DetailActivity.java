package com.cbk.TechTrollywood;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.util.List;

/**
 * activity for showing movie details
 */
public class DetailActivity extends AppCompatActivity {
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private Firebase fb;
    private List<String> extraData;
    private String rating;
    private ProgressDialog mAuthProgressDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = this;
        TextView movieName = (TextView) findViewById(R.id.movie_name_field);
        TextView synopsis = (TextView) findViewById(R.id.description_textview);
        fb = new Firebase(getResources().getString(R.string.firebase_url));
        Intent intent = getIntent();
        if (intent != null) {
            extraData = intent.getStringArrayListExtra("extra");//[0]=movie name [1]=id [2]=synmposis
            movieName.setText(extraData.get(0));
            getRating(extraData.get(1));
            synopsis.setText(extraData.get(2));
            //Log.d("TAG",extraData.toString());
        }
    }

    private static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    /**
     * detect rating clicks
     * @param view current view
     */
    public void onRatingClicked(View view) {
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rating_1:
                Toast.makeText(view.getContext(),
                        "You have selected 1",
                        Toast.LENGTH_SHORT).show();
                setRating(1, extraData.get(1));
                break;
            case R.id.rating_2:
                Toast.makeText(view.getContext(),
                        "You have selected 2",
                        Toast.LENGTH_SHORT).show();
                setRating(2, extraData.get(1));
                break;
            case R.id.rating_3:
                Toast.makeText(view.getContext(),
                        "You have selected 3",
                        Toast.LENGTH_SHORT).show();
                setRating(THREE, extraData.get(1));
                break;
            case R.id.rating_4:
                Toast.makeText(view.getContext(),
                        "You have selected 4",
                        Toast.LENGTH_SHORT).show();
                setRating(FOUR, extraData.get(1));
                break;
            case R.id.rating_5:
                Toast.makeText(view.getContext(),
                        "You have selected 5",
                        Toast.LENGTH_SHORT).show();
                setRating(FIVE, extraData.get(1));
                break;
        }
    }

    /**
     * helper method for setting the rating in firebase
     * @param rating rating to set
     * @param id movie id
     */
    private void setRating(int rating, String id) {
        AuthData authData = fb.getAuth();
        if (authData != null) {
            fb.child("movies").child(id).child("ratings").child(authData.getUid()).child("rating").setValue(rating);
            fb.child("movies").child(id).child("name").setValue(extraData.get(0));
        } else {
            Toast.makeText(getApplicationContext(), "No user authenticated",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * used for populating the ratings list on load
     * @param id movie id
     */
    private void getRating(String id) {
        AuthData authData = fb.getAuth();
        if (authData != null) {
            fb.child("movies").child(id).child("ratings").child(authData.getUid()).child("rating")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (active) {
                                mAuthProgressDialog = new ProgressDialog(context);
                                mAuthProgressDialog.setTitle("Please wait");
                                mAuthProgressDialog.setMessage("Fetching data...");
                                mAuthProgressDialog.setCancelable(false);
                                mAuthProgressDialog.show();
                            }
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
                            if (mAuthProgressDialog != null) {
                                mAuthProgressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Log.d("DetailActivity", "The read failed: " + firebaseError.getMessage());
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "No user authenticated",
                    Toast.LENGTH_LONG).show();
        }
    }
}
