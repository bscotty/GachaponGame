package com.pajato.gacha

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pajato.gacha.ui.PullListener
import com.pajato.gacha.ui.PullViewUpdater
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener(PullListener(root))
        this.lifecycle.addObserver(PullViewUpdater(root))
    }

}
