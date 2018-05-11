package com.pajato.gacha

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.pajato.gacha.database.FirebaseLoginManager
import com.pajato.gacha.model.character.Character
import com.pajato.gacha.model.event.RxBus
import com.pajato.gacha.model.event.UserEvent
import com.pajato.gacha.ui.CharacterListAdapter
import com.pajato.gacha.ui.CharacterListViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Consumer<UserEvent> {
    private lateinit var subscription: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFab.setOnClickListener {
            val intent = Intent(this, PullActivity::class.java)
            startActivityForResult(intent, MainActivity.PULL_REQUEST)
        }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        characterRecyclerView.layoutManager = layoutManager
        val adapter = CharacterListAdapter(listOf())
        characterRecyclerView.adapter = adapter

        this.lifecycle.addObserver(FirebaseLoginManager)
    }

    /** Subscribe to user events. */
    override fun onStart() {
        super.onStart()
        subscription = RxBus.subscribeToEventType(UserEvent::class.java, this)
    }

    /** Unsubscribe from events. */
    override fun onPause() {
        super.onPause()
        subscription.dispose()
    }

    /** If the UserEvent has no current user, initialize the sign in process. */
    override fun accept(t: UserEvent) {
        val user = t.getData()
        if (user == null) {
            val intent = Intent(this, SignInActivity::class.java)
            startActivityForResult(intent, SIGN_IN_REQUEST)
        } else {
            // load user data
            val liveData = CharacterListViewModel(user.uid).getLiveData()
            liveData.observe(this, Observer<DataSnapshot> { dataSnapshot ->
                if (dataSnapshot != null) {
                    val valueKeyMap: MutableMap<String, Int> = mutableMapOf()
                    for (child in dataSnapshot.children) {
                        val value: String = child.value.toString()
                        if (valueKeyMap.containsKey(value)) {
                            valueKeyMap[value] = valueKeyMap[value]!! + 1
                        } else {
                            valueKeyMap[value] = 1
                        }
                    }
                    val characterItems = convertToAdapterList(valueKeyMap)
                    (characterRecyclerView.adapter as CharacterListAdapter).setItems(characterItems)
                }
            })

        }
    }

    private fun convertToAdapterList(mutableMap: MutableMap<String, Int>): List<Pair<Character, Int>> {
        val list: MutableList<Pair<Character, Int>> = mutableListOf()
        for (key in mutableMap.keys) {
            val character = Character.getCharacter(key)
            list.add(Pair(character, mutableMap[key] ?: 1))
        }
        return list
    }

    companion object {
        const val SIGN_IN_REQUEST = 1
        const val PULL_REQUEST = 2 // :)
    }

}
