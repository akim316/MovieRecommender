package com.cbk.movierecommender;

import com.cbk.TechTrollywood.Database;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Andy on 4/7/2016.
 */
public class UnitTestMajor {


    @Test
    public void testMajor(){
        Database db = mock(Database.class);
        when(db.getStudentMajorFor("Andy")).thenReturn("CS");

        assertEquals("CS", db.getStudentMajorFor("Andy"));
        assertNull(db.getStudentMajorFor("ASDF"));
    }

}
