package com.latihanrakamin.aplikasisederhana.remote.model


import com.google.gson.annotations.SerializedName

data class GetTopHeadlinesSource(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)