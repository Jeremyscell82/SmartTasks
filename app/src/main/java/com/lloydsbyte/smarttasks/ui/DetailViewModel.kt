package com.lloydsbyte.smarttasks.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lloydsbyte.smarttasks.utils.TaskDetailButtonConstants.Companion._RESOLVED
import com.lloydsbyte.smarttasks.utils.TaskDetailButtonConstants.Companion._UNRESOLVED
import com.lloydsbyte.smarttasks.utils.database.AppDatabase
import com.lloydsbyte.smarttasks.utils.database.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {

    private val _taskModel = mutableStateOf<TaskModel>(TaskModel())
    val taskModel = _taskModel

    fun getTask(database: AppDatabase, taskId: String){
        viewModelScope.launch(Dispatchers.IO) {
            database.taskDao().getTaskDetails(taskId).collect { task ->
                _taskModel.value = task
            }
        }
    }

    fun updateTaskStatus(database: AppDatabase, ticketStatus: String) {
        val model = taskModel.value
        model.taskStatus = ticketStatus
        viewModelScope.launch(Dispatchers.IO) {
            database.taskDao().updateTask(model)
        }
    }

    fun isCompleted(): Boolean {
        return taskModel.value.taskStatus != _UNRESOLVED
    }
    fun isResolved(): Boolean {
        var isTaskResolved = false
        if (taskModel.value.taskStatus == _RESOLVED) {
            isTaskResolved = true
        }
        return isTaskResolved
    }

    fun updateTaskWithComment(database: AppDatabase, comment: String, ticketStatus: String){
        val model = taskModel.value
        model.taskStatus = ticketStatus
        model.comments = comment
        viewModelScope.launch(Dispatchers.IO) {
            database.taskDao().updateTask(model)
        }
    }
}