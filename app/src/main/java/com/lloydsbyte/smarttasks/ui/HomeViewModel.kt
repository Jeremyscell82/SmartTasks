package com.lloydsbyte.smarttasks.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lloydsbyte.smarttasks.utils.Utils
import com.lloydsbyte.smarttasks.utils.database.AppDatabase
import com.lloydsbyte.smarttasks.utils.database.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _listOfTasks = mutableStateListOf<TaskModel>()
    val taskList: List<TaskModel> = _listOfTasks

    private val todayInStr = Utils().getTodaysDate()
    private var dateInStr: String by mutableStateOf(todayInStr)
    var todayStr = ""
    var displayDate = mutableStateOf("")


    fun initViewModel(database: AppDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            database.taskDao().getDaysTasks(dateInStr).collect { taskList ->
                _listOfTasks.clear()
                _listOfTasks.addAll(
                    taskList.sortedWith(compareByDescending<TaskModel> { _task ->
                        _task.priority
                    }.thenComparing(compareBy { it.dueDate })
                    )
                )
            }
        }
        updateTitle()
    }

    private fun updateTitle() {
        val today = Utils().getTodaysDate()
        if (dateInStr == today) {
            displayDate.value = todayStr
        } else {
            displayDate.value = Utils().convertToUserFormat(dateInStr)
        }
    }


    fun showNextDay(database: AppDatabase) {
        val dateToRetrieve = Utils().getNextDay(dateInStr)
        dateInStr = dateToRetrieve
        initViewModel(database)
    }

    fun showPreviousDay(database: AppDatabase) {
        val dataToRetrieve = Utils().getPreviousDay(dateInStr)
        dateInStr = dataToRetrieve
        initViewModel(database)
    }


}