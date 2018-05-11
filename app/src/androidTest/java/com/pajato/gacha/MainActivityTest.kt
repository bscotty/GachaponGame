package com.pajato.gacha

import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.pajato.gacha.database.FirebaseLoginManager
import org.junit.After
import org.junit.Test

class MainActivityTest : ActivityTestBase<MainActivity>(MainActivity::class.java, false) {
    @After fun signOut() {
        FirebaseLoginManager.signOut()
    }

    /** Ensure that the main activity's initial state is as we expect. */
    @Test fun testSkipSignInInitialState() {
        skipSignIn()
        rule.launchActivity(null)

        checkViewVisibility(withId(R.id.root), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.mainFab), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.characterRecyclerView), ViewMatchers.Visibility.VISIBLE)
    }

    /** Ensure that when we are not signed in we are directed to the sign in activity. */
    @Test fun testStartSignedOutInitialState() {
        rule.launchActivity(null)
        waitForView(withId(R.id.signInFrame), 5000)

        checkViewDoesNotExist(withId(R.id.mainFab))
        checkViewDoesNotExist(withId(R.id.characterRecyclerView))
    }

}