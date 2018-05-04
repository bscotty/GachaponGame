package com.pajato.gacha.database

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pajato.gacha.model.event.RxBus
import com.pajato.gacha.model.event.UserEvent

object FirebaseLoginManager : LifecycleObserver, OnCompleteListener<AuthResult> {

    private val TAG = this::class.java.canonicalName
    private lateinit var mAuth: FirebaseAuth

    /** Gather the new FirebaseAuth instance. */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initializeAuth() {
        mAuth = FirebaseAuth.getInstance()
    }

    /** Get the current user and emit it to the activities that need it. */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        val user: FirebaseUser? = mAuth.currentUser
        RxBus.send(UserEvent(user))
    }

    /** Create a Firebase user. */
    fun createUser(email: String, password: String): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this)
    }

    /** Sign in a Firebase user using their name and password. */
    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this)
    }

    /** Log when the task completes and if the sign in is successful alert listeners to the new user. */
    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            Log.d(TAG, "User successfully obtained")
            val user: FirebaseUser = mAuth.currentUser!!
            RxBus.send(UserEvent(user))
        } else {
            Log.e(TAG, "User was not obtained: " + task.exception)
        }
    }

    fun signOut() {
        mAuth.signOut()
    }

}