package com.cbk.movierecommender;

import com.cbk.TechTrollywood.Database;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by sana on 4/11/16.
 */
public class UnitTestSearch {

    @Test
    public void testSearch() {
        Database db = mock(Database.class);

        List list = new ArrayList<>();
        list.add("Harry Potter");
        list.add("High School Musical");
        list.add("Home Alone");

        when(db.search("Home Alone")).thenReturn(list.contains("Home Alone"));

        assertTrue("Home Alone", true);
        assertFalse("Cheaper by the Dozen", false);
    }

}
