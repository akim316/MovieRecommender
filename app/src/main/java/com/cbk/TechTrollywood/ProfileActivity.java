package com.cbk.TechTrollywood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.login.LoginManager;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

/**
 * activity for showing the user profile and main screen
 */
public class ProfileActivity extends AppCompatActivity {
    private Firebase mFirebaseRef;
    private AuthData mAuthData;


    /* Called when activity is first created */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));
        mAuthData=mFirebaseRef.getAuth();
        //Log.d("profile","ProfileActivityCreated");
        if(mAuthData==null){
            this.finish();
        }

    }
    @Override
    public void onBackPressed()
    {
        logout();
        this.finish();
    }

    private void logout() {
        if (this.mAuthData != null) {
            String facebook = "facebook";
            /* logout of Firebase */
            mFirebaseRef.unauth();
            /* Logout of any of the Frameworks. This step is optional, but ensures the user is not logged into
             * Facebook/Google+ after logging out of Firebase. */
            if (this.mAuthData.getProvider().equals(facebook)) {
                /* Logout from Facebook */
                LoginManager.getInstance().logOut();
//            } else if (this.mAuthData.getProvider().equals("google") && mGoogleApiClient.isConnected()) {
//                /* Logout from Google+ */
//                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//                mGoogleApiClient.disconnect();
            }

        }
    }
}
