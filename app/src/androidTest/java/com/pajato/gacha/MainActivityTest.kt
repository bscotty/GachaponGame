package com.pajato.gacha

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.pajato.gacha.database.FirebaseLoginManager
import com.pajato.gacha.model.Character
import com.pajato.gacha.model.Puller
import com.pajato.gacha.model.Rarity
import com.pajato.gacha.model.event.PullEvent
import com.pajato.gacha.model.event.RxBus
import junit.framework.Assert.*
import kotlinx.android.synthetic.main.activity_main.*
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Test
import java.util.*

class MainActivityTest : ActivityTestBase<MainActivity>(MainActivity::class.java, false) {
    @After fun signOut() {
        FirebaseLoginManager.signOut()
    }

    /** Ensure that the main activity's initial state is as we expect. */
    @Test fun testSkipSignInInitialState() {
        skipSignIn()
        rule.launchActivity(null)
        waitForView(withId(R.id.fab), 5000)

        checkViewVisibility(withId(R.id.fab), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.revealView), ViewMatchers.Visibility.INVISIBLE)
        checkViewVisibility(withId(R.id.characterImage), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.fakeImageText), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.pullText), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.progressSpinner), ViewMatchers.Visibility.GONE)
    }

    /** Ensure that when we are not signed in we are directed to the sign in activity. */
    @Test fun testStartSignedOutInitialState() {
        rule.launchActivity(null)
        waitForView(withId(R.id.signInFrame), 5000)

        checkViewDoesNotExist(withId(R.id.fab))
        checkViewDoesNotExist(withId(R.id.revealView))
        checkViewDoesNotExist(withId(R.id.characterImage))
        checkViewDoesNotExist(withId(R.id.fakeImageText))
        checkViewDoesNotExist(withId(R.id.pullText))
        checkViewDoesNotExist(withId(R.id.progressSpinner))
    }

    /** Pull a character from the various buckets and ensure they are displayed. */
    @Test fun testPullCharacters() {
        skipSignIn()
        rule.launchActivity(null)
        waitForView(withId(R.id.fab), 5000)

        val commonInt = 1
        val uncommonInt = 46
        val rareInt = 71
        val superRareInt = 91

        val belowInt = 0
        val aboveInt = 101

        val common = Puller.getRandomCharacterFromInt(commonInt)
        val uncommon = Puller.getRandomCharacterFromInt(uncommonInt)
        val rare = Puller.getRandomCharacterFromInt(rareInt)
        val superRare = Puller.getRandomCharacterFromInt(superRareInt)

        val below = Puller.getRandomCharacterFromInt(belowInt)
        val above = Puller.getRandomCharacterFromInt(aboveInt)

        assertEquals("Character was not correct rarity", Rarity.COMMON, common.rarity)
        assertEquals("Character was not correct rarity", Rarity.UNCOMMON, uncommon.rarity)
        assertEquals("Character was not correct rarity", Rarity.RARE, rare.rarity)
        assertEquals("Character was not correct rarity", Rarity.SUPER_RARE, superRare.rarity)

        assertEquals("Character was not correct rarity", Rarity.COMMON, below.rarity)
        assertEquals("Character was not correct rarity", Rarity.COMMON, above.rarity)

        loadCharacter(common)
        loadCharacter(uncommon)
        loadCharacter(rare)
        loadCharacter(superRare)

        loadCharacter(below)
        loadCharacter(above)
    }


    /** Do a test pull with the fab to ensure that the pull works. */
    @Test fun testPullWithFab() {
        skipSignIn()
        rule.launchActivity(null)
        waitForView(withId(R.id.fab), 5000)

        onView(withId(R.id.fab))
                .perform(click())
        // Ensure that the pull spinner appears and that a character image is loaded.
        waitForView(withId(R.id.progressSpinner), 1000)
        waitForView(allOf(withId(R.id.characterImage), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)), 5000)

        // Assert that the reveal drawable is correct. Drawable comparisons should be done with Drawable.ConstantState
        val res = rule.activity.resources
        val listOfDrawables = listOf(res.getDrawable(R.drawable.gradient_common, null).constantState,
                res.getDrawable(R.drawable.gradient_uncommon, null).constantState,
                res.getDrawable(R.drawable.gradient_rare, null).constantState,
                res.getDrawable(R.drawable.gradient_super_rare, null).constantState)
        val displayedDrawable = rule.activity.revealView.drawable.constantState
        val isValidDrawableShown = displayedDrawable == listOfDrawables[0]
                || displayedDrawable == listOfDrawables[1] || displayedDrawable == listOfDrawables[2]
                || displayedDrawable == listOfDrawables[3]
        assertTrue("There is no valid drawable displayed after the pull", isValidDrawableShown)
    }

    /** Simulates a pull and determines that the reveal drawable is correct for the pull's rarity. */
    private fun loadCharacter(character: Character) {
        RxBus.send(PullEvent(character))
        waitForView(withId(R.id.progressSpinner), 1000)
        waitForView(allOf(withId(R.id.characterImage), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)), 5000)
        checkViewVisibility(withId(R.id.revealView), ViewMatchers.Visibility.VISIBLE)

        val resId = when (character.rarity) {
            Rarity.SUPER_RARE -> R.drawable.gradient_super_rare
            Rarity.RARE -> R.drawable.gradient_rare
            Rarity.UNCOMMON -> R.drawable.gradient_uncommon
            Rarity.COMMON -> R.drawable.gradient_common
        }
        val shouldBeDrawable = rule.activity.resources.getDrawable(resId, null).constantState
        val displayedDrawable = rule.activity.revealView.drawable.constantState
        assertEquals("Incorrect Reveal Drawable for character ${character.name}" +
                " of rarity ${character.rarity}", displayedDrawable, shouldBeDrawable)
    }

    /** Skips the sign in process. Note that this must run before the rule launches the activity. */
    private fun skipSignIn() {
        val email = "idk@dude.com"
        val password = "please help me"
        var waitBoy = false
        val timeWaiting = 5000

        FirebaseLoginManager.initializeAuth()
        FirebaseLoginManager.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    waitBoy = true
                }
        val cal = Calendar.getInstance()
        val endTime = cal.timeInMillis + timeWaiting
        while (!waitBoy && cal.timeInMillis < endTime) {
            // do nothing
        }
        if (!waitBoy) {
            assertFalse("Could not sign in in time.", true)
        }
    }
}