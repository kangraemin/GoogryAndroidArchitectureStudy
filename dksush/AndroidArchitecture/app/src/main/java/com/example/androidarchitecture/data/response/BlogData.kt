package com.example.androidarchitecture.data.response

import com.google.gson.annotations.SerializedName

data class BlogData(
    @SerializedName("bloggerlink")
    val bloggerLink: String,
    @SerializedName("bloggername")
    val bloggerName: String,
    val description: String,
    val link: String,
    val postdate: String,
    val title: String
)