package com.yaman.todolist.pages.mainfragment.ioc

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.yaman.core.constants.Constants
import com.yaman.core.networking.Scheduler
import com.yaman.todolist.pages.database.TodoDb
import com.yaman.todolist.pages.mainfragment.adapter.MainFragmentListAdapter
import com.yaman.todolist.pages.mainfragment.model.MainFragmentDataContract
import com.yaman.todolist.pages.mainfragment.model.MainFragmentLocalData
import com.yaman.todolist.pages.mainfragment.model.MainFragmentRepository
import com.yaman.todolist.pages.mainfragment.viewmodel.MainFragmentViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
@MainFragmentScope
class MainFragmentModule(private val activity: Activity) {

    @Provides
    fun colorListViewModelFactory(
            repository: MainFragmentDataContract.Repository,
            compositeDisposable: CompositeDisposable
    ): MainFragmentViewModelFactory = MainFragmentViewModelFactory(repository, compositeDisposable)


    @Provides
    fun colorListRepo(
            local: MainFragmentDataContract.Local,
            scheduler: Scheduler,
            compositeDisposable: CompositeDisposable
    ): MainFragmentDataContract.Repository = MainFragmentRepository(local, scheduler, compositeDisposable)

    @Provides
    fun provideLayoutManager(context: Context): LinearLayoutManager = LinearLayoutManager(context)

    @Provides
    fun adapter(): MainFragmentListAdapter = MainFragmentListAdapter(activity)

    @Provides
    fun localHomeDb(
            todoDb: TodoDb,
            scheduler: Scheduler
    ): MainFragmentDataContract.Local = MainFragmentLocalData(todoDb, scheduler)

    @Provides
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun todoDb(context: Context): TodoDb = Room.databaseBuilder(
            context,
            TodoDb::class.java, Constants.ToDoDbName.DB_NAME
    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
}