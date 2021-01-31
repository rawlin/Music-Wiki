package com.example.musicwiki.models.artisttopalbums


import com.google.gson.annotations.SerializedName

data class Album(
    val artist: Artist,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val playcount: Int,
    val url: String
)