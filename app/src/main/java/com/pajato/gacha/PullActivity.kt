package com.pajato.gacha

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pajato.gacha.model.Puller
import com.pajato.gacha.ui.ImageLoader
import com.pajato.gacha.ui.PullViewUpdater
import kotlinx.android.synthetic.main.activity_pull.*

class PullActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull)
        fab.setOnClickListener { _ ->
            Puller.pull()
        }
        this.lifecycle.addObserver(PullViewUpdater(root))
        this.lifecycle.addObserver(ImageLoader)
    }

}
