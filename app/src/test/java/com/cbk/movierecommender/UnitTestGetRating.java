package com.cbk.movierecommender;

import com.cbk.TechTrollywood.Database;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Amy on 4/6/2016.
 */
public class UnitTestGetRating {
    @Test

    public void getRatingTest() {
        Database db = mock(Database.class);

        Map movieMap = new HashMap();
        movieMap.put("The Fault in Our Stars", 1);
        movieMap.put("Stardust", 2);
        movieMap.put("The Princess Bride", 3);
        movieMap.put("Star!", 4);
        movieMap.put("Frozen", 5);


        when(db.getRating("The Fault in Our Stars")).thenReturn(1);
        when(db.getRating("Stardust")).thenReturn(2);
        when(db.getRating("The Princess Bride")).thenReturn(3);
        when(db.getRating("Star!")).thenReturn(4);
        when(db.getRating("Frozen")).thenReturn(5);

        assertEquals(movieMap.get("The Fault in Our Stars"), db.getRating("The Fault in Our Stars"));
        assertEquals(movieMap.get("Stardust"), db.getRating("Stardust"));
        assertEquals(movieMap.get("The Princess Bride"), db.getRating("The Princess Bride"));
        assertEquals(movieMap.get("Star!"), db.getRating("Star!"));
        assertEquals(movieMap.get("Frozen"), db.getRating("Frozen"));
    }
}
