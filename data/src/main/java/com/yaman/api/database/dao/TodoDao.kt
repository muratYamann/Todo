package com.yaman.api.database.dao

import androidx.room.*
import com.yaman.api.response.model.todo.TodoItem
import io.reactivex.Flowable

@Dao
interface TodoDao : BaseDao<TodoItem> {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insert(vararg obj: TodoItem)


    @Query("UPDATE todo_table SET description=:desc, title=:title WHERE id=:id")
    fun update(id: Int, title: String, desc: String)

    @Delete
    fun deleteTask(todoItem: TodoItem)

    @Query("DELETE FROM todo_table")
    fun deleteAll()

    @Query("SELECT * from todo_table ORDER BY title ASC")
    fun getAllWords(): Flowable<List<TodoItem>>

    @Query("UPDATE todo_table SET isComplete = :mark WHERE id=:id")
    fun completion(id: Int, mark: Boolean)
}