package com.yaman.todolist.pages.mainfragment.model

import com.yaman.api.response.model.todo.TodoItem
import com.yaman.core.networking.Result
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

interface MainFragmentDataContract {

    interface Repository {
        val wordListResult: PublishSubject<Result<List<TodoItem>>>

        fun getWordList()


        fun <T> handleError(result: PublishSubject<Result<T>>, error: Throwable)
        fun insertTodoItem(todoItem: TodoItem)
        fun update(id: Int, title: String, desctription: String)
        fun marksAsComplete(id: Int, mark: Boolean)
        fun delete(todoItem: TodoItem)
    }

    interface Local {
        fun update(id: Int, title: String, desctription: String)
        fun insertTodoItem(todoItem: TodoItem)
        fun getWordList(): Flowable<List<TodoItem>>
        fun markAsComplete(id: Int, mark: Boolean)
        fun delete(todoItem: TodoItem)
    }
}
