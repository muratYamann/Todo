package com.yaman.todolist.pages.mainfragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yaman.todolist.pages.mainfragment.model.MainFragmentDataContract
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class MainFragmentViewModelFactory(
        private val repository: MainFragmentDataContract.Repository,
        private val compositeDisposable: CompositeDisposable
) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainFragmentViewModel(repository, compositeDisposable) as T
    }
}
