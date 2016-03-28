package com.cbk.TechTrollywood;

import java.util.ArrayList;

/**
 * Created by alex9 on 3/9/2016.
 */
public class Movie {
    private ArrayList<String> genre;
    private int id;
    private String title;
    private int year;
    private int rating;
    private int rating2;
    private String thumbnailUrl;

    public Movie() {

    }
    public Movie(ArrayList<String> genre, int id, String title, int year, int rating, String thumbnailUrl) {
        this.genre = genre;
        this.id = id;
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.thumbnailUrl = thumbnailUrl;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating2() {
        return rating2;
    }

    public void setRating2(int rating2) {
        this.rating2 = rating2;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
