package com.pajato.gacha

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pajato.gacha.database.FirebaseLoginManager
import com.pajato.gacha.model.Puller
import com.pajato.gacha.model.event.RxBus
import com.pajato.gacha.model.event.UserEvent
import com.pajato.gacha.ui.ImageLoader
import com.pajato.gacha.ui.PullViewUpdater
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Consumer<UserEvent> {
    private lateinit var subscription: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener { _ ->
            Puller.pull()
        }
        this.lifecycle.addObserver(PullViewUpdater(root))
        this.lifecycle.addObserver(FirebaseLoginManager)
        this.lifecycle.addObserver(ImageLoader)
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
        }
    }

    companion object {
        const val SIGN_IN_REQUEST = 1
    }

}
