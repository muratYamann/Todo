package com.yaman.todolist.pages.connectionfailed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yaman.todolist.pages.splash.SplashActivity
import kotlinx.android.synthetic.main.connection_failed_fragment.*
import com.yaman.todolist.R
import org.jetbrains.anko.intentFor


class ConnectionFailedActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connection_failed_fragment)


        btnTryAgainConnection!!.setOnClickListener {

            startActivity(intentFor<SplashActivity>())
            finish()

        }

    }

}
