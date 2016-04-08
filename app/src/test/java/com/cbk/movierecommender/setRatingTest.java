package com.cbk.movierecommender;

/**
 * Created by sana on 4/6/16.
 */

import org.junit.Test;

import com.cbk.TechTrollywood.R;
import com.firebase.client.AuthData;

import com.firebase.client.Firebase;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class setRatingTest {
    @Test
    private Firebase fb = new Firebase(getResources().getString(R.string.firebase_url));

    assertNull(fb.getAuth());
    assertNotNull(fb.getAuth());


    );
}

