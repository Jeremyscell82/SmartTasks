package com.lloydsbyte.smarttasks.utils.network

import com.google.gson.annotations.SerializedName

class TaskResponse {

    data class MainResponse(
        @SerializedName("tasks")
        val tasks: List<TaskResponse>
    )

    data class TaskResponse(
        @SerializedName("id")
        val id: String,
        @SerializedName("TargetDate")
        val targetDate: String,
        @SerializedName("DueDate")
        val dueDate: String,
        @SerializedName("Title")
        val title: String,
        @SerializedName("Description")
        val description: String,
        @SerializedName("Priority")
        val priority: Int
    )
}