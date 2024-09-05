package com.lloydsbyte.smarttasks.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskModel::class], version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {

        private var instance: AppDatabase? = null
        val DB_Name = "com.lloydsbyte.smarttasks.db"

        fun getDatabase(applicationContext: Context): AppDatabase {
            if (instance == null){
                instance = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    DB_Name
                ).build()
            }
            return instance!!
        }
    }
}