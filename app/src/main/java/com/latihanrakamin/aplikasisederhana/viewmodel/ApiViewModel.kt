package com.latihanrakamin.aplikasisederhana.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.latihanrakamin.aplikasisederhana.remote.model.GetTopHeadlinesResponse
import com.latihanrakamin.aplikasisederhana.repository.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    val getTopHeadlines = MutableLiveData<Response<GetTopHeadlinesResponse>>()
    val errorMessage = MutableLiveData<String>()

    fun getTopHeadlines() {
        apiRepository.getTopHeadlines().enqueue(object : Callback<GetTopHeadlinesResponse> {
            override fun onResponse(call: Call<GetTopHeadlinesResponse>, response: Response<GetTopHeadlinesResponse>) {
                getTopHeadlines.postValue(response)
            }

            override fun onFailure(call: Call<GetTopHeadlinesResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}