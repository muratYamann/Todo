package com.yaman.todolist.pages.mainfragment.ioc

import com.yaman.core.ioc.CoreComponent
import com.yaman.todolist.pages.NewTaskFragment
import com.yaman.todolist.pages.mainfragment.MainFragment
import dagger.Component

@MainFragmentScope
@Component(dependencies = [CoreComponent::class], modules = [MainFragmentModule::class])
interface MainFragmentComponent {
    fun inject(mainFragment: MainFragment)
    fun injectNewTastkComponent(newTaskFragment: NewTaskFragment)
}

