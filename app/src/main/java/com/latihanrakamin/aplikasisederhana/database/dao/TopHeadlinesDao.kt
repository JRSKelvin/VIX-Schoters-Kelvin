package com.latihanrakamin.aplikasisederhana.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.latihanrakamin.aplikasisederhana.database.data.TopHeadlinesData

@Dao
interface TopHeadlinesDao {
    @Query("SELECT * FROM TopHeadlinesData")
    fun getTopHeadlines(): LiveData<List<TopHeadlinesData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopHeadlines(account: TopHeadlinesData): Long

    @Update
    fun updateTopHeadlines(account: TopHeadlinesData): Int

    @Delete
    fun deleteTopHeadlines(account: TopHeadlinesData): Int
}