package com.yaman.todolist

import android.app.Activity
import com.yaman.core.application.CoreApp
import com.yaman.todolist.pages.mainfragment.ioc.DaggerMainFragmentComponent
import com.yaman.todolist.pages.mainfragment.ioc.MainFragmentComponent
import com.yaman.todolist.pages.mainfragment.ioc.MainFragmentModule
import javax.inject.Singleton

@Singleton
object ComponentManager {

    private var mainFragmentComponent:MainFragmentComponent? = null



    fun mainFragmentComponent(activity: Activity): MainFragmentComponent {
        if (mainFragmentComponent == null)
            mainFragmentComponent = DaggerMainFragmentComponent.builder()
                    .coreComponent(CoreApp.coreComponent).mainFragmentModule(MainFragmentModule(activity))
                    .build()
        return mainFragmentComponent as MainFragmentComponent
    }

    fun destroymainFragmentComponent() {
        mainFragmentComponent = null
    }




}
