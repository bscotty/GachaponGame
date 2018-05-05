package com.pajato.gacha.model.event

import com.google.firebase.auth.FirebaseUser

/** Used for user sign in changes. */
class UserEvent(private var user: FirebaseUser?) : Event {
    override fun getData(): FirebaseUser? {
        return user
    }
}