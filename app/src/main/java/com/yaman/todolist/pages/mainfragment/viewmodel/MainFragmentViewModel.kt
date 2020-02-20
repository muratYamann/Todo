package com.yaman.todolist.pages.mainfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yaman.api.response.model.todo.TodoItem
import com.yaman.core.extensions.toLiveData
import com.yaman.core.networking.Result
import com.yaman.todolist.ComponentManager
import com.yaman.todolist.pages.mainfragment.model.MainFragmentDataContract
import io.reactivex.disposables.CompositeDisposable

class MainFragmentViewModel(
        val repository: MainFragmentDataContract.Repository,
        private val compositeDisposable: CompositeDisposable
) : ViewModel() {


    val getAllwordsResult: LiveData<Result<List<TodoItem>>> by lazy {
        repository.wordListResult.toLiveData(compositeDisposable)
    }


    fun getAllwordsResult() {
        repository.getWordList()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        ComponentManager.destroymainFragmentComponent()
    }

    fun insert(todoItem: TodoItem) {
        repository.insertTodoItem(todoItem)
    }

    fun update(id: Int, title: String, desctription: String) {
        repository.update(id,title,desctription)
    }

    fun markAsComplete(id: Int, mark: Boolean) {
        repository.marksAsComplete(id,mark)
    }

    fun delete(todoItem: TodoItem) {
        repository.delete(todoItem)
    }
}



