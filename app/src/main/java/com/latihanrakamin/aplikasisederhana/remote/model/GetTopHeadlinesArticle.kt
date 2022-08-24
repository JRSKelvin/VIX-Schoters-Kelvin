package com.latihanrakamin.aplikasisederhana.remote.model


import com.google.gson.annotations.SerializedName

data class GetTopHeadlinesArticle(
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val getTopHeadlinesSource: GetTopHeadlinesSource,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
)