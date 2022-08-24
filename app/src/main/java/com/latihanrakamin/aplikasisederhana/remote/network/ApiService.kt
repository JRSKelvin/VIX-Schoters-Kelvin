package com.latihanrakamin.aplikasisederhana.remote.network

import com.latihanrakamin.aplikasisederhana.remote.model.GetTopHeadlinesResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("top-headlines?country=id&apiKey=$API_KEY")
    fun getTopHeadlines(): Call<GetTopHeadlinesResponse>

    companion object {
        private const val API_KEY = ApiKey.API_KEY
    }
}