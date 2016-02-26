package com.cbk.TechTrollywood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends Fragment {
    private Button searchMovies;
    private Button recentMovies;
    private Button recentDVDs;

    public ProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        searchMovies = (Button) view.findViewById(R.id.search_movies_button);
        searchMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        recentMovies=(Button) view.findViewById(R.id.recent_movies_button);
        recentMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RecentActivity.class);
                startActivity(intent);
            }
        });

        recentDVDs=(Button) view.findViewById(R.id.recent_dvds_button);
        recentDVDs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DVDActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
