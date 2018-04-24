package com.pajato.gacha.model.event

import android.graphics.Bitmap

class ImageLoadedEvent(private val bitmap: Bitmap) : Event {
    override fun getData(): Bitmap {
        return bitmap
    }
}