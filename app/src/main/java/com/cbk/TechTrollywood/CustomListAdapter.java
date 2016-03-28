package com.cbk.TechTrollywood;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alex9 on 3/9/2016.
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Movie> movies;

    public CustomListAdapter(Activity activity, List<Movie> movies) {
        this.activity = activity;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

//        if (imageLoader == null)
//            imageLoader = AppController.getInstance().getImageLoader();
        ImageView thumbnail = (ImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView rating2 = (TextView) convertView.findViewById(R.id.rating2);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        Movie m = movies.get(position);

        // thumbnail image
        Picasso.with(activity.getApplicationContext()).load(m.getThumbnailUrl()).into(thumbnail);
        //thumbnail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());

        // rating
        rating.setText("Audience Score: " + String.valueOf(m.getRating()) + "%");
        if (m.getRating2() == -1) {
            rating2.setText("Critics Score: N/A");
        } else {
            rating2.setText("Critics Score: " + String.valueOf(m.getRating2()) + "%");
        }


        // genre
//        String genreStr = "";
//        for (String str : m.getGenre()) {
//            genreStr += str + ", ";
//        }
//        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
//                genreStr.length() - 2) : genreStr;
//        genre.setText(genreStr);

        // release year
        if (m.getYear() == 0) {
            year.setText("Unknown");
        } else {
            year.setText(String.valueOf(m.getYear()));
        }


        return convertView;
    }
}
