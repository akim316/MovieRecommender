package com.cbk.TechTrollywood;

<<<<<<< Updated upstream


=======
import java.util.HashMap;
>>>>>>> Stashed changes
import java.util.List;
import java.util.Map;

public abstract class Database {
    List recommendationsList = null;
    List ratingsList = null;
    List moviesList =  null;

    public abstract String getStudentMajorFor(String major);
    public abstract Boolean setStudentMajor(String name, String major);

    public List getRecommendations(String searchMajor, String searchRating) {
        return recommendationsList;
    }

<<<<<<< Updated upstream
    public List setRating(int rating, String id) {
        return ratingsList;
    }

    public boolean search(String movie) {
        return moviesList.contains(movie);
    }
=======
    public abstract int getRating(String id);
>>>>>>> Stashed changes
}
