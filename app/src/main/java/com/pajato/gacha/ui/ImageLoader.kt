package com.pajato.gacha.ui

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.AppCompatImageView
import com.pajato.gacha.model.Character
import com.pajato.gacha.model.event.ImageErrorEvent
import com.pajato.gacha.model.event.ImageLoadedEvent
import com.pajato.gacha.model.event.PullEvent
import com.pajato.gacha.model.event.RxBus
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.InputStream
import java.net.URL

object ImageLoader : Consumer<PullEvent>, LifecycleObserver {
    private lateinit var subscription: Disposable

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        subscription = RxBus.subscribeToEventType(PullEvent::class.java, this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        subscription.dispose()
    }

    override fun accept(t: PullEvent) {
        loadImage(t.getData())
    }

    fun loadImage(character: Character) {
        lateinit var bitmap: Bitmap
        Completable.fromAction {
            val i: InputStream = URL(character.url).openStream()
            bitmap = BitmapFactory.decodeStream(i)
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        RxBus.send(ImageLoadedEvent(bitmap))
                    }

                    override fun onError(e: Throwable) {
                        RxBus.send(ImageErrorEvent(e))
                    }
                })
    }

    /** Provide a more modularized way to load images, with optional callbacks. */
    fun loadImage(character: Character, imageView: AppCompatImageView,
                  onSuccess: (() -> Unit?) = {}, onFailure: (() -> Unit?) = {}) {
        lateinit var bitmap: Bitmap
        Completable.fromAction {
            val i: InputStream = URL(character.url).openStream()
            bitmap = BitmapFactory.decodeStream(i)
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        imageView.setImageBitmap(bitmap)
                        onSuccess()
                    }

                    override fun onError(e: Throwable) {
                        onFailure()
                    }

                })
    }

}