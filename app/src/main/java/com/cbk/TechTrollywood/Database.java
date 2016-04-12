package com.cbk.TechTrollywood;



import java.util.List;

public abstract class Database {
    List recommendationsList = null;
    List ratingsList = null;
    List moviesList =  null;

    public abstract String getStudentMajorFor(String major);
    public abstract Boolean setStudentMajor(String name, String major);

    public List getRecommendations(String searchMajor, String searchRating) {
        return recommendationsList;
    }

    public List setRating(int rating, String id) {
        return ratingsList;
    }

    public boolean search(String movie) {
        return moviesList.contains(movie);
    }
}
