package com.latihanrakamin.aplikasisederhana.repository

import com.latihanrakamin.aplikasisederhana.remote.network.ApiService

class ApiRepository(private val apiService: ApiService) {
    fun getTopHeadlines() = apiService.getTopHeadlines()
}