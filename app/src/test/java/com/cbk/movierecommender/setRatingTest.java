package com.cbk.movierecommender;

/**
 * Created by sana on 4/6/16.
 */

import org.junit.Test;

import com.cbk.TechTrollywood.R;
import android.test.InstrumentationTestCase;
import com.firebase.client.AuthData;

import com.firebase.client.Firebase;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class setRatingTest extends InstrumentationTestCase {
    @Test
    private Firebase fb = new Firebase(getInstrumentation().getContext().getResources().getString(R.string.firebase_url));
    AuthData ad = fb.getAuth();

    assertNull(ad);
    assertNotNull(ad);
    );
}

