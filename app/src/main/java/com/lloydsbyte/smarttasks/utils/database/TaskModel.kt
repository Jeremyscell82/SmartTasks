package com.lloydsbyte.smarttasks.utils.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "taskModel")
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    val dbKey: Long =0L,
    @SerializedName("task_id")
    val taskId: String ="",
    @SerializedName("target_date")
    val targetDate: String = "",
    @SerializedName("due_date")
    val dueDate: String? = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("priority")
    val priority: Int = 0,
    @SerializedName("status")
    var taskStatus: String = "",
    @SerializedName("comments")
    var comments: String = ""
)