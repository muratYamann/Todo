package com.yaman.todolist.pages.mainfragment.model

import com.yaman.api.response.model.todo.TodoItem
import com.yaman.core.networking.Scheduler
import com.yaman.todolist.pages.database.TodoDb
import io.reactivex.Flowable

class MainFragmentLocalData(private val todoDb: TodoDb, private val scheduler: Scheduler) : MainFragmentDataContract.Local {

    override fun update(id: Int, title: String, desctription: String) = todoDb.todoDao().update(id, title, desctription)

    override fun insertTodoItem(todoItem: TodoItem) = todoDb.todoDao().insert(todoItem)

    override fun getWordList(): Flowable<List<TodoItem>> = todoDb.todoDao().getAllWords()

    override fun markAsComplete(id: Int, mark: Boolean) = todoDb.todoDao().completion(id, mark)

    override fun delete(todoItem: TodoItem) = todoDb.todoDao().deleteTask(todoItem)
}
