package com.cbk.movierecommender;

import com.cbk.TechTrollywood.Database;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by michellechiu on 4/11/16.
 */
public class UnitTestSetRating {

    @Test
    public void testSetRating() {
        Database db = mock(Database.class);
        List emptyList = new ArrayList<>();
        List movieRating1 = new ArrayList<>();
        List movieRating2 = new ArrayList<>();
        List movieRating3 = new ArrayList<>();
        List movieRating4 = new ArrayList<>();
        List movieRating5 = new ArrayList<>();

        movieRating1.add("The Fault in Our Stars");
        movieRating2.add("Stardust");
        movieRating3.add("The Princess Bride");
        movieRating4.add("Star!");
        movieRating5.add("Frozen");

        when(db.setRating(1, "The Fault in Our Stars")).thenReturn(movieRating1);
        when(db.setRating(2, "Stardust")).thenReturn(movieRating2);
        when(db.setRating(3, "The Princess Bride")).thenReturn(movieRating3);
        when(db.setRating(4, "Star!")).thenReturn(movieRating4);
        when(db.setRating(5, "Frozen")).thenReturn(movieRating5);

        assertEquals(movieRating1, db.setRating(1, "The Fault in Our Stars"));
        assertEquals(movieRating2, db.setRating(2, "Stardust"));
        assertEquals(movieRating3, db.setRating(3, "The Princess Bride"));
        assertEquals(movieRating4, db.setRating(4, "Star!"));
        assertEquals(movieRating5, db.setRating(5, "Frozen"));
    }

}
