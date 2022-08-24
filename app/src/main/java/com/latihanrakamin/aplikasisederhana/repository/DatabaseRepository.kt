package com.latihanrakamin.aplikasisederhana.repository

import androidx.lifecycle.LiveData
import com.latihanrakamin.aplikasisederhana.database.AppDatabase
import com.latihanrakamin.aplikasisederhana.database.data.TopHeadlinesData

class DatabaseRepository(databaseInstance: AppDatabase) {
    private val topHeadlineDao = databaseInstance.TopHeadlinesDao()

    fun getTopHeadlines(): LiveData<List<TopHeadlinesData>> {
        return topHeadlineDao.getTopHeadlines()
    }

    fun insertTopHeadlines(dataRequest: TopHeadlinesData): Long {
        return topHeadlineDao.insertTopHeadlines(dataRequest)
    }

    fun updateTopHeadlines(dataRequest: TopHeadlinesData): Int {
        return topHeadlineDao.updateTopHeadlines(dataRequest)
    }

    fun deleteTopHeadlines(dataRequest: TopHeadlinesData): Int {
        return topHeadlineDao.deleteTopHeadlines(dataRequest)
    }
}