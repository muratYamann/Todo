package com.yaman.api.response.model.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoItem(
    @ColumnInfo(name = "title") val title: String, @ColumnInfo(name = "dueDate") val time: String,
    @ColumnInfo(name = "tag") val tag: String, @ColumnInfo(name = "isComplete") val isComplete: Boolean,
    @ColumnInfo(name = "description") val description: String, @ColumnInfo(name = "color") val color: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
