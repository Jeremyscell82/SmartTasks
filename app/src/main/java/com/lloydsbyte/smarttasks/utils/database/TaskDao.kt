package com.lloydsbyte.smarttasks.utils.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(taskModel: TaskModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(taskModel: TaskModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskList(taskModels: List<TaskModel>)

    @Query("SELECT * FROM taskModel")
    fun getTasks(): Flow<List<TaskModel>>

    @Query("SELECT * FROM taskModel WHERE dbKey iS :dbKey")
    fun getTaskDetails(dbKey: String): Flow<TaskModel>

    @Query("SELECT * FROM taskModel WHERE targetDate IS :targetDate")
    fun getDaysTasks(targetDate: String): Flow<List<TaskModel>>

    @Delete
    fun deleteMe(taskModel: TaskModel)

    @Query("SELECT COUNT(*) FROM taskModel")
    fun getTaskCount(): Int

}