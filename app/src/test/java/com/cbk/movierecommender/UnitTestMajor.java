package com.cbk.movierecommender;

import com.cbk.TechTrollywood.Database;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
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
        when(db.setStudentMajor("Andy", "CS")).thenReturn(true);

        assertEquals("CS", db.getStudentMajorFor("Andy"));
        assertNull(db.getStudentMajorFor("ASDF"));

        assertEquals( true, (boolean) db.setStudentMajor("Andy","CS"));
        assertFalse(db.setStudentMajor("ASDF", "CS"));
    }

}
