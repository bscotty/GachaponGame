package com.pajato.gacha.ui

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.graphics.Bitmap
import android.support.v7.widget.AppCompatImageView
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.pajato.gacha.R
import com.pajato.gacha.model.Character
import com.pajato.gacha.model.Rarity
import com.pajato.gacha.model.event.*
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlin.math.hypot

class PullViewUpdater(private val root: ViewGroup) : Consumer<Event>, LifecycleObserver {
    private lateinit var subscription: Disposable
    private lateinit var character: Character
    private lateinit var bitmap: Bitmap

    private val progressSpinner: ProgressBar = root.findViewById(R.id.progressSpinner)
    private val pullText: TextView = root.findViewById(R.id.pullText)
    private val characterImage: AppCompatImageView = root.findViewById(R.id.characterImage)
    private val fakeImage: TextView = root.findViewById(R.id.fakeImage)
    private val revealView: AppCompatImageView = root.findViewById(R.id.revealView)

    /** Accept both ImageLoadedEvents and PullEvents. */
    override fun accept(t: Event?) {
        when (t) {
            is ImageLoadedEvent -> accept(t)
            is PullEvent -> accept(t)
            is ImageErrorEvent -> accept(t)
            else -> Log.v(this.javaClass.canonicalName, "Miscellaneous Event Loaded: " + t.toString())
        }
    }

    /** When accepting a PullEvent, prep the layout to display the new pull's animation. This is the "loading" stage. */
    private fun accept(t: PullEvent) {
        character = t.getData()

        // Ensure that the UI is changed purely on the main thread.
        Completable.fromAction {
            characterImage.visibility = View.INVISIBLE
            fakeImage.visibility = View.INVISIBLE
            progressSpinner.visibility = View.VISIBLE
            revealView.visibility = View.INVISIBLE
            pullText.visibility = View.INVISIBLE
        }.subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    /** When we acecpt an ImageErrorEvent, display the character with a default image. */
    private fun accept(t: ImageErrorEvent) {
        val e = t.getData()
        Log.e(this.javaClass.canonicalName, "Async Task to Load Image Failed: " + e.message)
        e.printStackTrace()
        displayCharacter(false)
    }

    /** When we accept an ImageLoadedEvent, get the bitmap and display the character with it. */
    private fun accept(t: ImageLoadedEvent) {
        bitmap = t.getData()
        displayCharacter(true)
    }

    /** Stage the view to display character name and image with a specialized reveal animation. */
    private fun displayCharacter(hasValidBitmap: Boolean) {
        Completable.fromAction {
            // Stage the layout for our view.
            characterImage.visibility = View.VISIBLE
            progressSpinner.visibility = View.GONE
            pullText.visibility = View.VISIBLE
            pullText.text = character.name

            // If the bitmap is valid use it, otherwise use a default image.
            if (hasValidBitmap) {
                characterImage.setImageBitmap(bitmap)
                fakeImage.visibility = View.INVISIBLE
            } else {
                characterImage.setImageResource(R.drawable.ic_photo_filter_black_24dp)
                fakeImage.visibility = View.VISIBLE
            }

            // Different character rarities should have different backgrounds.
            val resourceId = when (character.rarity) {
                Rarity.COMMON -> R.drawable.gradient_common
                Rarity.UNCOMMON -> R.drawable.gradient_uncommon
                Rarity.RARE -> R.drawable.gradient_rare
                Rarity.SUPER_RARE -> R.drawable.gradient_super_rare
            }
            revealView.setImageResource(resourceId)

            // Do the reveal animation.
            val cx = characterImage.x + (characterImage.width / 2)
            val cy = characterImage.y - (characterImage.height / 2)
            val finalRadius = hypot(cx, root.height - cy)
            val anim = ViewAnimationUtils.createCircularReveal(revealView, cx.toInt(), cy.toInt(), 0f, finalRadius)
            revealView.visibility = View.VISIBLE
            anim.start()
        }.subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    /** When we resume, resubscribe to events. */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun initializeEvents() {
        subscription = RxBus.subscribeToEventType(Event::class.java, this)
    }

    /** When we pause, dispose of events to avoid multiple events. */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun removeEvents() {
        subscription.dispose()
    }

}