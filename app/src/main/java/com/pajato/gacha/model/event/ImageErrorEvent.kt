package com.pajato.gacha.model.event

class ImageErrorEvent(private var exception: Throwable) : Event {
    override fun getData(): Throwable {
        return exception
    }
}