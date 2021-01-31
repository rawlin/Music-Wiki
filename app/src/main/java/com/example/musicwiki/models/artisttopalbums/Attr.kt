package com.example.musicwiki.models.artisttopalbums


import com.google.gson.annotations.SerializedName

data class Attr(
    val artist: String,
    val page: String,
    val perPage: String,
    val total: String,
    val totalPages: String
)