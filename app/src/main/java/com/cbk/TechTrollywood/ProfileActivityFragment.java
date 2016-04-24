package com.cbk.TechTrollywood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends Fragment {
    private Firebase fb;
    private TextView profileName;
    private TextView profileMajor;
    private EditText nameField;
    private EditText majorField;
    private EditText newPasswordField;
    private EditText oldPasswordField;
    private String email;
    private Context context;



    public ProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        context=getContext();
        fb = new Firebase(getResources().getString(R.string.firebase_url));
        Button searchMovies = (Button) view.findViewById(R.id.search_movies_button);
        searchMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        Button recentMovies=(Button) view.findViewById(R.id.recent_movies_button);
        recentMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RecentActivity.class);
                startActivity(intent);
            }
        });

        Button recentDVDs=(Button) view.findViewById(R.id.recent_dvds_button);
        recentDVDs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DVDActivity.class);
                startActivity(intent);
            }
        });

        Button setNameButton=(Button)view.findViewById(R.id.set_name_button);
        setNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileName.setText(nameField.getText());
                setName();
            }
        });

        Button setMajorButton=(Button)view.findViewById(R.id.set_major_button);
        setMajorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileMajor.setText(majorField.getText());
                setMajor();
            }
        });

        Button setPasswordButton=(Button)view.findViewById(R.id.set_password_button);
        setPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fb.changePassword(email, oldPasswordField.getText().toString(), newPasswordField.getText().toString(), new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "Password Changed",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(context, firebaseError.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        Button recommendationsButton=(Button)view.findViewById(R.id.recommendations_button);
        recommendationsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), RecommendedActivity.class);
                startActivity(intent);
            }
        });
        
        profileName=(TextView)view.findViewById(R.id.profile_name);
        profileMajor=(TextView)view.findViewById(R.id.profile_major);
        nameField=(EditText)view.findViewById(R.id.change_name_field);
        majorField=(EditText)view.findViewById(R.id.change_major_field);
        oldPasswordField=(EditText)view.findViewById(R.id.old_password_editText);
        newPasswordField=(EditText)view.findViewById(R.id.new_password_editext);
        populateFields();
        return view;
    }

    /**
     * sets the user's name in firebase
     */
    private void setName(){
        AuthData authData = fb.getAuth();
        if (authData != null) {
            fb.child("users").child(authData.getUid()).child("Name").setValue(nameField.getText().toString());
        } else {
            Toast.makeText(getContext(), "No user authenticated",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * sets the user's major in firebase
     */
    private void setMajor(){
        AuthData authData = fb.getAuth();
        if (authData != null) {
            fb.child("users").child(authData.getUid()).child("Major").setValue(majorField.getText().toString());
        } else {
            Toast.makeText(getContext(), "No user authenticated",
                    Toast.LENGTH_LONG).show();
        }

    }


    private void populateFields() {
        AuthData authData = fb.getAuth();
        if (authData != null) {
            fb.child("users").child(authData.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.child("Name").getValue() != null) {
                        profileName.setText(snapshot.child("Name").getValue().toString());
                    }
                    if (snapshot.child("Major").getValue() != null) {
                        profileMajor.setText(snapshot.child("Major").getValue().toString());
                    }
                    email=snapshot.child("email").getValue().toString();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d("ProfileActivity", "The read failed: " + firebaseError.getMessage());
                }
            });
        }
    }

}
