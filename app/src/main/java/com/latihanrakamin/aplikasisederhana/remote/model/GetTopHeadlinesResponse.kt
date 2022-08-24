package com.latihanrakamin.aplikasisederhana.remote.model


import com.google.gson.annotations.SerializedName

data class GetTopHeadlinesResponse(
    @SerializedName("articles")
    val getTopHeadlinesArticles: List<GetTopHeadlinesArticle>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)