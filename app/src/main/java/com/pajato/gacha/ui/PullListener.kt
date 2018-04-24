package com.pajato.gacha.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import com.pajato.gacha.model.Character
import com.pajato.gacha.model.Puller
import com.pajato.gacha.model.event.ImageErrorEvent
import com.pajato.gacha.model.event.ImageLoadedEvent
import com.pajato.gacha.model.event.RxBus
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.InputStream
import java.net.URL

class PullListener(val root: ViewGroup) : View.OnClickListener {
    private lateinit var current: Character
    private lateinit var bm: Bitmap

    override fun onClick(v: View?) {
        Completable.fromAction {
            current = Puller.pull()
            val i: InputStream = URL(current.url).openStream()
            bm = BitmapFactory.decodeStream(i)
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        RxBus.send(ImageLoadedEvent(bm))
                    }

                    override fun onError(e: Throwable) {
                        RxBus.send(ImageErrorEvent(e))
                    }
                })
    }

}