package com.latihanrakamin.aplikasisederhana.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.latihanrakamin.aplikasisederhana.repository.ApiRepository

class ApiViewModelFactory(private val apiRepository: ApiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ApiViewModel::class.java)) {
            ApiViewModel(this.apiRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}