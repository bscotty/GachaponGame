package com.pajato.gacha

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import com.pajato.gacha.database.FirebaseLoginManager
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Test
import java.util.*

class SignInActivityTest : ActivityTestBase<SignInActivity>(SignInActivity::class.java) {

    /** Ensure that we force a sign out after each test ends to avoid interfering with other tests. */
    @After fun signOut() {
        FirebaseLoginManager.signOut()
    }

    /** Ensure that when we initialize the sign in activity, the layout is as we expect. */
    @Test fun testInitialState() {
        // Sign in options should be visible.
        checkViewVisibility(withId(R.id.signInEmailLayout), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.signInGoogleLayout), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.signInFacebookLayout), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.signInTwitterLayout), ViewMatchers.Visibility.VISIBLE)

        // Email and password views should not exist.
        checkViewDoesNotExist(withId(R.id.errorMessage))
        checkViewDoesNotExist(withId(R.id.inputLayoutEmail))
        checkViewDoesNotExist(withId(R.id.inputLayoutPassword))
        checkViewDoesNotExist(withId(R.id.emailPasswordSignInButton))
    }

    /** Ensure that when we navigate to signing in with email and password, the layout is as we expect. */
    @Test fun testEmailPasswordState() {
        onView(withId(R.id.signInEmailLayout))
                .perform(click())

        // Email and password views should now be visible.
        checkViewVisibility(withId(R.id.errorMessage), ViewMatchers.Visibility.INVISIBLE)
        checkViewVisibility(withId(R.id.inputLayoutEmail), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.inputLayoutPassword), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.emailPasswordSignInButton), ViewMatchers.Visibility.VISIBLE)

        // Sign in options should no longer exist.
        checkViewDoesNotExist(withId(R.id.signInEmailLayout))
        checkViewDoesNotExist(withId(R.id.signInGoogleLayout))
        checkViewDoesNotExist(withId(R.id.signInFacebookLayout))
        checkViewDoesNotExist(withId(R.id.signInTwitterLayout))
    }

    /** Ensure that alternate methods of signing in safely alert users that they are not complete. */
    @Test fun testFutureFeatures() {
        val signInPreamble = "Signing in with "
        val signInPostable = " is a feature in progress! Thanks for your patience."
        val google = "Google"
        val facebook = "Facebook"
        val twitter = "Twitter"
        val waitTime: Long = 3000

        val googleViewMatcher = withText(signInPreamble + google + signInPostable)
        val facebookViewMatcher = withText(signInPreamble + facebook + signInPostable)
        val twitterViewMatcher = withText(signInPreamble + twitter + signInPostable)

        onView(withId(R.id.signInGoogleLayout))
                .perform(click())
        waitForView(googleViewMatcher, waitTime)
        checkViewVisibility(googleViewMatcher, ViewMatchers.Visibility.VISIBLE)

        onView(withId(R.id.signInFacebookLayout))
                .perform(click())
        waitForView(facebookViewMatcher, waitTime)
        checkViewVisibility(facebookViewMatcher, ViewMatchers.Visibility.VISIBLE)

        onView(withId(R.id.signInTwitterLayout))
                .perform(click())
        waitForView(twitterViewMatcher, waitTime)
        checkViewVisibility(twitterViewMatcher, ViewMatchers.Visibility.VISIBLE)
    }

    /** Ensure that new accounts can be created. */
    @Test fun testSuccessfulCreation() {
        // Navigate to the sign in email/password layout and specify a valid email/password combo.
        onView(withId(R.id.signInEmailLayout))
                .perform(click())

        // ensure that the account used is a new account each time. this email does not have to be a
        // "valid" email in the sense that it can send or receive messages, it just has to be an
        // appropriate format and cannot already be in the database. NOTE: This will cause an additional
        // "user" to appear in the firebase console with each run. They should be deleted afterward.
        val email = "support-" + Calendar.getInstance().timeInMillis.toString() + "@pajato.com"
        val password = "123abcxyz"

        // Insert the data into the input views and try to initialize the create user process.
        onView(withId(R.id.inputEmail))
                .perform(replaceText(email))
        onView(withId(R.id.inputPassword))
                .perform(replaceText(password))
        onView(withId(R.id.emailPasswordSignInButton))
                .perform(click())

        checkViewVisibility(withId(R.id.errorMessage), ViewMatchers.Visibility.INVISIBLE)
        // TODO: Find an alternate way to test that we have begun the sign in process. Thread.sleep() is bad
        Thread.sleep(3000)
        assertTrue("Activity did not finish in time.", this.rule.activity.isFinishing)
    }

    /** Ensure that the "account creation" screen can also serve correctly as a standard sign in. */
    @Test fun testSuccessfulEmailSignIn() {
        // Navigate to the sign in email/password layout and specify a predetermined email/password combo.
        onView(withId(R.id.signInEmailLayout))
                .perform(click())
        val email = "idk@dude.com"
        val password = "please help me"

        // Insert the data into the input views and try to initialize the create user process.
        onView(withId(R.id.inputEmail))
                .perform(replaceText(email))
        onView(withId(R.id.inputPassword))
                .perform(replaceText(password))
        onView(withId(R.id.emailPasswordSignInButton))
                .perform(click())

        checkViewVisibility(withId(R.id.errorMessage), ViewMatchers.Visibility.INVISIBLE)
        // TODO: Find an alternate way to test that we have begun the sign in process. Thread.sleep() is bad
        Thread.sleep(3000)
        assertTrue("Activity did not finish in time.", this.rule.activity.isFinishing)
    }
}