package com.lloydsbyte.smarttasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lloydsbyte.smarttasks.utils.TaskDetailButtonConstants.Companion._UNRESOLVED
import com.lloydsbyte.smarttasks.utils.database.AppDatabase
import com.lloydsbyte.smarttasks.utils.database.TaskModel
import com.lloydsbyte.smarttasks.utils.network.NetworkClient
import com.lloydsbyte.smarttasks.utils.network.NetworkConstants
import com.lloydsbyte.smarttasks.utils.network.TaskResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel: ViewModel() {

    fun pullTasks(database: AppDatabase) {
        val client = NetworkClient.client(NetworkConstants.baseUrl)
        viewModelScope.launch(Dispatchers.IO) {

            val response = client.getTaskList()


            if (response.isSuccessful){
                if (response.body() != null){

                    Timber.d("JL_ ${response.body()!!.tasks.size}")
                    val storedTasks = database.taskDao().getTasks().first()
                    val convertedTasks = convertResponse(response.body()!!)
                    val differences = convertedTasks.filter { it.taskId !in storedTasks.map { _task -> _task.taskId } }
                    Timber.d("JL_ added tasks: ${differences.size}")
                    if (differences.isNotEmpty()) {
                        database.taskDao().addTaskList(differences)
                    }

//                    database.taskDao().addTaskList(convertResponse(response.body()!!))


                    val storedCount = database.taskDao().getTaskCount()


                    Timber.d("JL_ DB count is: $storedCount")


                }
            }

        }
    }

    private fun convertResponse(response: TaskResponse.MainResponse): List<TaskModel> {
        return response.tasks.map { responseTask ->
            TaskModel(
                dbKey = 0,
                taskId = responseTask.id,
                targetDate = responseTask.targetDate,
                dueDate = responseTask.dueDate,
                title = responseTask.title,
                description = responseTask.description,
                priority = responseTask.priority,
                taskStatus = _UNRESOLVED,
                comments = ""
            )
        }
    }
}