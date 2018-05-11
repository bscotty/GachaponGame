package com.pajato.gacha.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import com.google.firebase.database.*
import com.pajato.gacha.database.FirebaseDatabaseManager.USERS_KEY


class CharacterListViewModel(uid: String) : ViewModel() {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(USERS_KEY)
    private val data = FirebaseQueryLiveData(databaseReference.child(uid))

    fun getLiveData(): LiveData<DataSnapshot> {
        return data
    }

    private inner class FirebaseQueryLiveData(val query: Query) : LiveData<DataSnapshot>() {
        private val listener = MyValueEventListener()

        constructor(ref: DatabaseReference) : this(ref as Query)

        override fun onActive() {
            Log.d(TAG, "onActive")
            query.addValueEventListener(listener)
        }

        override fun onInactive() {
            Log.d(TAG, "onInactive")
            query.removeEventListener(listener)
        }

        private inner class MyValueEventListener : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                value = dataSnapshot
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Can't listen to query $query", databaseError.toException())
            }
        }
    }

    companion object {
        const val TAG = "FirebaseQueryLiveData"
    }
}