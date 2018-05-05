package com.pajato.gacha

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import org.junit.Test

class SignInActivityErrorTest : ActivityTestBase<SignInActivity>(SignInActivity::class.java) {

    /** Ensure that not entering an email is handled properly during account creation. */
    @Test fun testNoEmail() {
        Espresso.onView(ViewMatchers.withId(R.id.signInEmailLayout))
                .perform(ViewActions.click())

        val errorMessage = this.rule.activity.getString(R.string.errorNoEmail)
        Espresso.onView(ViewMatchers.withId(R.id.emailPasswordSignInButton))
                .perform(ViewActions.click())

        // Wait for the error text to appear and ensure it matches the no email error text.
        waitForView(ViewMatchers.withText(errorMessage), 3000)
        checkViewVisibility(ViewMatchers.withId(R.id.errorMessage), ViewMatchers.Visibility.VISIBLE)
        Espresso.onView(ViewMatchers.withId(R.id.errorMessage))
                .check(ViewAssertions.matches(ViewMatchers.withText(errorMessage)))

    }

    /** Ensure that not entering a password is handled properly during account creation. */
    @Test fun testNoPassword() {
        Espresso.onView(ViewMatchers.withId(R.id.signInEmailLayout))
                .perform(ViewActions.click())

        val errorMessage = this.rule.activity.getString(R.string.errorNoPassword)
        val email = "idk@dude.com"

        // Insert the data into the input views and try to initialize the create user process.
        Espresso.onView(ViewMatchers.withId(R.id.inputEmail))
                .perform(ViewActions.replaceText(email))
        Espresso.onView(ViewMatchers.withId(R.id.emailPasswordSignInButton))
                .perform(ViewActions.click())

        // Wait for the error text to appear and ensure it matches the no password error text.
        waitForView(ViewMatchers.withText(errorMessage), 3000)
        checkViewVisibility(ViewMatchers.withId(R.id.errorMessage), ViewMatchers.Visibility.VISIBLE)
        Espresso.onView(ViewMatchers.withId(R.id.errorMessage))
                .check(ViewAssertions.matches(ViewMatchers.withText(errorMessage)))
    }

    /** Ensure that invalid emails are caught and handled properly during account creation. */
    @Test fun testInvalidEmail() {
        // Navigate to the sign in email/password layout and specify an invalid email
        Espresso.onView(ViewMatchers.withId(R.id.signInEmailLayout))
                .perform(ViewActions.click())
        val emailDoesNotExist = "invalidEmail7134@dude"
        val password = "123abcxyz"
        val errorMessage = rule.activity.getString(R.string.errorInvalidEmail)

        // Insert the data into the input views and try to initialize the create user process.
        Espresso.onView(ViewMatchers.withId(R.id.inputEmail))
                .perform(ViewActions.replaceText(emailDoesNotExist))
        Espresso.onView(ViewMatchers.withId(R.id.inputPassword))
                .perform(ViewActions.replaceText(password))
        Espresso.onView(ViewMatchers.withId(R.id.emailPasswordSignInButton))
                .perform(ViewActions.click())

        // Wait for the error text to appear and ensure it matches the invalid email error text.
        waitForView(ViewMatchers.withText(errorMessage), 3000)
        checkViewVisibility(ViewMatchers.withId(R.id.errorMessage), ViewMatchers.Visibility.VISIBLE)
        Espresso.onView(ViewMatchers.withId(R.id.errorMessage))
                .check(ViewAssertions.matches(ViewMatchers.withText(errorMessage)))
    }

    /** Ensure that email collisions are caught and handled properly during account creation. */
    @Test fun testEmailCollision() {
        // Navigate to the sign in email/password layout and specify an already used email
        Espresso.onView(ViewMatchers.withId(R.id.signInEmailLayout))
                .perform(ViewActions.click())
        val emailCollision = "idk@dude.com"
        val password = "123abcxyz"
        val errorMessage = rule.activity.getString(R.string.errorEmailCollision)

        // Insert the data into the input views and try to initialize the create user process.
        Espresso.onView(ViewMatchers.withId(R.id.inputEmail))
                .perform(ViewActions.replaceText(emailCollision))
        Espresso.onView(ViewMatchers.withId(R.id.inputPassword))
                .perform(ViewActions.replaceText(password))
        Espresso.onView(ViewMatchers.withId(R.id.emailPasswordSignInButton))
                .perform(ViewActions.click())

        // Wait for the error text to appear and ensure it matches the email collision error text.
        waitForView(ViewMatchers.withText(errorMessage), 3000)
        checkViewVisibility(ViewMatchers.withId(R.id.errorMessage), ViewMatchers.Visibility.VISIBLE)
        Espresso.onView(ViewMatchers.withId(R.id.errorMessage))
                .check(ViewAssertions.matches(ViewMatchers.withText(errorMessage)))
    }

    /** Ensure that invalid passwords are caught and handled properly during account creation. */
    @Test fun testInvalidPassword() {
        // Navigate to the sign in email/password layout and specify an invalid password
        Espresso.onView(ViewMatchers.withId(R.id.signInEmailLayout))
                .perform(ViewActions.click())
        val emailDoesNotExist = "invalidEmail7134@dude.com"
        val password = "12"
        val errorMessage = rule.activity.getString(R.string.errorInvalidPassword)

        // Insert the data into the input views and try to initialize the create user process.
        Espresso.onView(ViewMatchers.withId(R.id.inputEmail))
                .perform(ViewActions.replaceText(emailDoesNotExist))
        Espresso.onView(ViewMatchers.withId(R.id.inputPassword))
                .perform(ViewActions.replaceText(password))
        Espresso.onView(ViewMatchers.withId(R.id.emailPasswordSignInButton))
                .perform(ViewActions.click())

        // Wait for the error text to appear and ensure it matches the invalid password error text.
        waitForView(ViewMatchers.withText(errorMessage), 3000)
        checkViewVisibility(ViewMatchers.withId(R.id.errorMessage), ViewMatchers.Visibility.VISIBLE)
        Espresso.onView(ViewMatchers.withId(R.id.errorMessage))
                .check(ViewAssertions.matches(ViewMatchers.withText(errorMessage)))
    }
}