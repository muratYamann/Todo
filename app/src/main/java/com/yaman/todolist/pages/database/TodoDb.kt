package com.yaman.todolist.pages.database

import com.yaman.api.database.dao.TodoDao
import com.yaman.api.response.model.todo.TodoItem
import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [TodoItem::class], version = 3, exportSchema = false)

abstract class TodoDb : RoomDatabase() {

    abstract fun todoDao(): TodoDao
/**
    companion object {
        @Volatile
        private var INSTANCE: TodoDb? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TodoDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDb::class.java,
                    "Word_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
**/
    }
