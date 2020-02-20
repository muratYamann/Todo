package com.yaman.todolist.pages.mainfragment.model

import com.yaman.api.response.model.todo.TodoItem
import com.yaman.core.extensions.*
import com.yaman.core.networking.Result
import com.yaman.core.networking.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class MainFragmentRepository(
        private val local: MainFragmentDataContract.Local,
        private val scheduler: Scheduler,
        private val compositeDisposable: CompositeDisposable
) : MainFragmentDataContract.Repository {
    override val wordListResult: PublishSubject<Result<List<TodoItem>>> = PublishSubject.create()

    override fun getWordList() {
        wordListResult.loading(true)
        local.getWordList()
                .performOnBackOutOnMain(scheduler)
                .subscribe({ remote ->
                    wordListResult.success(remote)
                }, { error -> handleError(wordListResult, error) })
                .addTo(compositeDisposable)
    }

    override fun <T> handleError(result: PublishSubject<Result<T>>, error: Throwable) {
        result.failed(error)
        Timber.e(error.localizedMessage)
    }

    override fun insertTodoItem(todoItem: TodoItem) {
        wordListResult.loading(true)
        local.insertTodoItem(todoItem)
    }

    override fun update(id: Int, title: String, desctription: String) {
        local.update(id, title, desctription)
    }

    override fun marksAsComplete(id: Int, mark: Boolean) {
        local.markAsComplete(id, mark)
    }

    override fun delete(todoItem: TodoItem) {
        local.delete(todoItem)
    }
}
