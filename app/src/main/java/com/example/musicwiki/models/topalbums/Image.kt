package com.example.musicwiki.models.topalbums


import com.google.gson.annotations.SerializedName

data class Image(
    val size: String,
    @SerializedName("#text")
    val text: String
)