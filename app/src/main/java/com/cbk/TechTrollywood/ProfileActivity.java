package com.cbk.TechTrollywood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;

public class ProfileActivity extends AppCompatActivity {
    private Firebase mFirebaseRef;


    /* Called when activity is first created */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));
    }
    @Override
    public void onBackPressed()
    {
        mFirebaseRef.unauth();
        this.finish();
    }
}
