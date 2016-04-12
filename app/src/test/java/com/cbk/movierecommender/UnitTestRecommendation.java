package com.cbk.movierecommender;

import com.cbk.TechTrollywood.Database;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitTestRecommendation {


    @Test
    public void testRecommendations() {
        Database db = mock(Database.class);

        List list = new ArrayList<>();
        list.add("The Lord of the Rings: Return of the Kings");
        list.add("The Princess Bride");
        list.add("Frozen");
        List empty = new ArrayList<>();
        when(db.getRecommendations("CS", "5")).thenReturn(list);

        assertEquals(list, db.getRecommendations("CS", "5"));
        assertEquals(empty, db.getRecommendations("abcd", "5"));
        assertEquals(empty, db.getRecommendations("", "5"));
    }
}
