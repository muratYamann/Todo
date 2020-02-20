package com.yaman.todolist.pages.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.yaman.core.application.BaseActivity
import com.yaman.core.utils.ConnectionUtil
import com.yaman.todolist.BuildConfig
import com.yaman.todolist.R
import com.yaman.todolist.pages.home.MainActivity
import com.yaman.todolist.pages.connectionfailed.ConnectionFailedActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop


class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (BuildConfig.DEBUG) {
            Log.d("className: ", this.javaClass.simpleName)
        }

        val handler = Handler()
        handler.postDelayed({ jump() }, 2000)

    }

    private fun jump() {

        if (ConnectionUtil.isNetworkAvailable(applicationContext)) {
            // startActivity(intentFor<HomeActivity>().singleTop())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            this.finish()
        } else {
            startActivity(intentFor<ConnectionFailedActivity>().singleTop())
            this.finish()
        }
    }


}
