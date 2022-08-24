package com.latihanrakamin.aplikasisederhana.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.latihanrakamin.aplikasisederhana.database.dao.TopHeadlinesDao
import com.latihanrakamin.aplikasisederhana.database.data.TopHeadlinesData

@Database(entities = [TopHeadlinesData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun TopHeadlinesDao(): TopHeadlinesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "MainDatabase.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}