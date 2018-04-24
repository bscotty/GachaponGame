package com.pajato.gacha.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.AppCompatImageView
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.pajato.gacha.R
import com.pajato.gacha.model.Character
import com.pajato.gacha.model.Puller
import com.pajato.gacha.model.Rarity
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.*
import java.io.InputStream
import java.net.URL
import kotlin.math.hypot

class PullListener(val root: ViewGroup) : CompletableObserver, View.OnClickListener {
    private val progressSpinner: ProgressBar = root.progressSpinner
    private val pullText: TextView = root.pullText
    private val characterImage: AppCompatImageView = root.characterImage
    private val fakeImage: TextView = root.fakeImage
    private val revealView: AppCompatImageView = root.revealView

    private lateinit var current: Character
    private lateinit var bm: Bitmap

    override fun onClick(v: View?) {
        Completable.fromAction {
            current = Puller.pull()
            try {
                val i: InputStream = URL(current.url).openStream()
                bm = BitmapFactory.decodeStream(i)
            } catch (e: Exception) {
                Log.e("Load Image Failed", e.message)
                e.printStackTrace()
            }
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this)
    }

    override fun onComplete() {
        characterImage.visibility = View.VISIBLE
        pullText.visibility = View.VISIBLE
        progressSpinner.visibility = View.GONE
        pullText.text = current.name
        if (current.url != "") {
            characterImage.setImageBitmap(bm)
            fakeImage.visibility = View.INVISIBLE
        } else {
            characterImage.setImageResource(R.drawable.ic_photo_filter_black_24dp)
            fakeImage.visibility = View.VISIBLE
        }

        val resourceId = when (current.rarity) {
            Rarity.COMMON -> R.drawable.gradient_common
            Rarity.UNCOMMON -> R.drawable.gradient_uncommon
            Rarity.RARE -> R.drawable.gradient_rare
            Rarity.SUPER_RARE -> R.drawable.gradient_super_rare
        }
        revealView.setImageResource(resourceId)

        val cx = characterImage.x + (characterImage.width / 2)
        val cy = characterImage.y - (characterImage.height / 2)
        val finalRadius = hypot(cx, root.height - cy)
        val anim = ViewAnimationUtils.createCircularReveal(revealView, cx.toInt(), cy.toInt(), 0f, finalRadius)
        revealView.visibility = View.VISIBLE
        anim.start()
    }

    override fun onSubscribe(d: Disposable) {
        characterImage.visibility = View.INVISIBLE
        fakeImage.visibility = View.INVISIBLE
        progressSpinner.visibility = View.VISIBLE
        revealView.visibility = View.INVISIBLE
        pullText.visibility = View.INVISIBLE
    }

    override fun onError(e: Throwable) {
        Log.e(this.javaClass.canonicalName, "The following happened while trying to pull a character: " + e.message)
    }

}