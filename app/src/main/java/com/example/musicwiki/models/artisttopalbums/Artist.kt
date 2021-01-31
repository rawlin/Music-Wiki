package com.example.musicwiki.models.artisttopalbums


import com.google.gson.annotations.SerializedName

data class Artist(
    val mbid: String,
    val name: String,
    val url: String
)