package com.example.musicwiki.models.topalbums


import com.google.gson.annotations.SerializedName

data class AttrX(
    val page: String,
    val perPage: String,
    val tag: String,
    val total: String,
    val totalPages: String
)