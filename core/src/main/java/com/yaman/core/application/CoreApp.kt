package com.yaman.core.application

import android.app.Application
import com.yaman.core.R
import com.yaman.core.ioc.AppModule
import com.yaman.core.ioc.CoreComponent
import com.yaman.core.ioc.DaggerCoreComponent
import com.yaman.core.networking.synk.Synk
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

open class CoreApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initSynk()
        initDI()
        setupDefaultFont()

    }

    private fun initSynk() {
        Synk.init(this)
    }

    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }

    private fun setupDefaultFont() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Proxima_Nova_Sbold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}

