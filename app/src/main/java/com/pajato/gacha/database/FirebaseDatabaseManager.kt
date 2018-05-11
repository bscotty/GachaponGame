package com.pajato.gacha.database

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.google.android.gms.common.api.internal.LifecycleCallback
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pajato.gacha.model.event.PullEvent
import com.pajato.gacha.model.event.RxBus
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

object FirebaseDatabaseManager : LifecycleObserver, Consumer<PullEvent> {
    lateinit var database: FirebaseDatabase
    lateinit var subscription: Disposable

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initialize() {
        database = FirebaseDatabase.getInstance()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun subscribe() {
        subscription = RxBus.subscribeToEventType(PullEvent::class.java, this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun unsubscribe() {
        subscription.dispose()
    }

    /** Add the new character to the database. */
    override fun accept(t: PullEvent) {
        val character = t.getData()
        val currentUser = FirebaseLoginManager.getCurrentUser()
        if (currentUser != null) {
            val users = USERS_KEY
            val uid = currentUser.uid
            val reference: DatabaseReference = database.reference.child(users).child(uid)
            val key = reference.push().key
            reference.updateChildren(mapOf(key to character.getValue()))
        } else {
            Log.v(this::class.java.canonicalName, "Invalid User, skipping storing character.")
        }
    }

    const val USERS_KEY = "users"
}