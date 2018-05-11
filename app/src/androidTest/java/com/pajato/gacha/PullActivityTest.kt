package com.pajato.gacha

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.pajato.gacha.model.Character
import com.pajato.gacha.model.Puller
import com.pajato.gacha.model.Rarity
import com.pajato.gacha.model.event.PullEvent
import com.pajato.gacha.model.event.RxBus
import junit.framework.Assert
import kotlinx.android.synthetic.main.activity_pull.*
import org.hamcrest.CoreMatchers
import org.junit.Test

class PullActivityTest : ActivityTestBase<PullActivity>(PullActivity::class.java, false) {

    /** Ensure the layout is as we expect which allows us to make some assumptions in other tests. */
    @Test fun testInitialState() {
        rule.launchActivity(null)
        waitForView(ViewMatchers.withId(R.id.fab), 5000)

        checkViewVisibility(withId(R.id.fab), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.revealView), ViewMatchers.Visibility.INVISIBLE)
        checkViewVisibility(withId(R.id.characterImage), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.fakeImageText), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.pullText), ViewMatchers.Visibility.VISIBLE)
        checkViewVisibility(withId(R.id.progressSpinner), ViewMatchers.Visibility.GONE)
    }

    /** Pull a character from the various buckets and ensure they are displayed. */
    @Test fun testPullCharacters() {
        skipSignIn()
        rule.launchActivity(null)
        waitForView(ViewMatchers.withId(R.id.fab), 5000)

        // Set up some constant for our character loads processes.
        val commonInt = 1
        val uncommonInt = 46
        val rareInt = 71
        val superRareInt = 91

        val belowInt = 0
        val aboveInt = 101

        // Get a random character from each of the buckets.
        val common = Puller.getRandomCharacterFromInt(commonInt)
        val uncommon = Puller.getRandomCharacterFromInt(uncommonInt)
        val rare = Puller.getRandomCharacterFromInt(rareInt)
        val superRare = Puller.getRandomCharacterFromInt(superRareInt)

        val below = Puller.getRandomCharacterFromInt(belowInt)
        val above = Puller.getRandomCharacterFromInt(aboveInt)

        // Ensure our pulls from the buckets have the correct rarities.
        Assert.assertEquals("Character was not correct rarity", Rarity.COMMON, common.rarity)
        Assert.assertEquals("Character was not correct rarity", Rarity.UNCOMMON, uncommon.rarity)
        Assert.assertEquals("Character was not correct rarity", Rarity.RARE, rare.rarity)
        Assert.assertEquals("Character was not correct rarity", Rarity.SUPER_RARE, superRare.rarity)

        Assert.assertEquals("Character was not correct rarity", Rarity.COMMON, below.rarity)
        Assert.assertEquals("Character was not correct rarity", Rarity.COMMON, above.rarity)

        // Ensure that the layout is properly updated with each of the rarity buckets.
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
        waitForView(ViewMatchers.withId(R.id.fab), 5000)

        Espresso.onView(ViewMatchers.withId(R.id.fab))
                .perform(ViewActions.click())
        // Ensure that the pull spinner appears and that a character image is loaded.
        waitForView(ViewMatchers.withId(R.id.progressSpinner), 1000)
        waitForView(CoreMatchers.allOf(ViewMatchers.withId(R.id.characterImage), ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)), 5000)

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
        Assert.assertTrue("There is no valid drawable displayed after the pull", isValidDrawableShown)
    }

    /** Simulates a pull and determines that the reveal drawable is correct for the pull's rarity. */
    private fun loadCharacter(character: Character) {
        RxBus.send(PullEvent(character))
        waitForView(ViewMatchers.withId(R.id.progressSpinner), 1000)
        waitForView(CoreMatchers.allOf(ViewMatchers.withId(R.id.characterImage), ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)), 5000)
        checkViewVisibility(ViewMatchers.withId(R.id.revealView), ViewMatchers.Visibility.VISIBLE)

        val resId = when (character.rarity) {
            Rarity.SUPER_RARE -> R.drawable.gradient_super_rare
            Rarity.RARE -> R.drawable.gradient_rare
            Rarity.UNCOMMON -> R.drawable.gradient_uncommon
            Rarity.COMMON -> R.drawable.gradient_common
        }
        val shouldBeDrawable = rule.activity.resources.getDrawable(resId, null).constantState
        val displayedDrawable = rule.activity.revealView.drawable.constantState
        Assert.assertEquals("Incorrect Reveal Drawable for character ${character.name}" +
                " of rarity ${character.rarity}", displayedDrawable, shouldBeDrawable)
    }
}