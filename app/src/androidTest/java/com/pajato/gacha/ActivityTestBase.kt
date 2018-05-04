package com.pajato.gacha

import android.app.Activity
import android.support.test.espresso.*
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isRoot
import android.support.test.espresso.util.HumanReadables
import android.support.test.espresso.util.TreeIterables
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.runner.RunWith
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
abstract class ActivityTestBase<T : Activity>(theClass: Class<T>, shouldStart: Boolean = true) {
    @Rule
    @JvmField
    var rule: ActivityTestRule<T> = IntentsTestRule(theClass, false, shouldStart)

    /** Provide an extension on the Activity class to run code on the UI thread. */
    fun Activity.runOnUiThread(f: () -> Unit) {
        runOnUiThread { f() }
    }

    /** Check that a view's (via the given matcher) has the given visibility. */
    protected fun checkViewVisibility(viewMatcher: Matcher<View>, state: ViewMatchers.Visibility) {
        Espresso.onView(viewMatcher).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(state)))
    }

    /** Check that a view (via a given matcher) is not present in the current layout */
    protected fun checkViewDoesNotExist(viewMatcher: Matcher<View>) {
        Espresso.onView(viewMatcher).check(ViewAssertions.doesNotExist())
    }

    /** Wait for a view that matches the specified ViewMatcher */
    protected fun waitForView(viewMatcher: Matcher<View>, millis: Long) {
        onView(ViewMatchers.isRoot()).perform(ViewWaiter(viewMatcher, millis))
    }

    /** Based heavily on StackOverflow code by users Oleksandr Kucherenko and Michał Tajchert, found
     *  here: https://stackoverflow.com/questions/21417954/espresso-thread-sleep */
    protected class ViewWaiter(private val viewMatcher: Matcher<View>, private val millis: Long) : ViewAction {
        override fun getDescription(): String {
            return "Wait for a specific view with specified ViewMatcher: " + viewMatcher.toString() + " for " + millis + " ms"
        }

        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun perform(uiController: UiController?, view: View?) {
            uiController?.loopMainThreadUntilIdle()
            val startTime: Long = System.currentTimeMillis()
            val endTime: Long = startTime + millis
            do {
                for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                    if (viewMatcher.matches(child)) {
                        return
                    }
                }
                uiController?.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)

            throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(TimeoutException())
                    .build()
        }
    }

}