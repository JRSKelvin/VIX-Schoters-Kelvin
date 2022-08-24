package com.latihanrakamin.aplikasisederhana.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.latihanrakamin.aplikasisederhana.database.AppDatabase
import com.latihanrakamin.aplikasisederhana.database.data.TopHeadlinesData
import com.latihanrakamin.aplikasisederhana.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {
    private val databaseInstance = AppDatabase.getInstance(application)
    private val databaseRepository: DatabaseRepository = DatabaseRepository(databaseInstance)

    fun getTopHeadlines(): LiveData<List<TopHeadlinesData>> {
        return databaseRepository.getTopHeadlines()
    }

    fun insertTopHeadlines(dataRequest: TopHeadlinesData): MutableLiveData<Long> {
        val returnedData = MutableLiveData<Long>()
        viewModelScope.launch(Dispatchers.IO) {
            returnedData.postValue(databaseRepository.insertTopHeadlines(dataRequest))
        }
        return returnedData
    }

    fun updateTopHeadlines(dataRequest: TopHeadlinesData): LiveData<Int> {
        val returnedData = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            returnedData.postValue(databaseRepository.updateTopHeadlines(dataRequest))
        }
        return returnedData
    }

    fun deleteTopHeadlines(dataRequest: TopHeadlinesData): LiveData<Int> {
        val returnedData = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            returnedData.postValue(databaseRepository.deleteTopHeadlines(dataRequest))
        }
        return returnedData
    }
}