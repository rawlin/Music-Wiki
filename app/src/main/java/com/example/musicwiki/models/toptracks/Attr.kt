package com.example.musicwiki.models.toptracks


import com.google.gson.annotations.SerializedName

data class Attr(
    val page: String,
    val perPage: String,
    val tag: String,
    val total: String,
    val totalPages: String
)