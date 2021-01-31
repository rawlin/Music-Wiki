package com.example.musicwiki.models.artistdetails


import com.google.gson.annotations.SerializedName

data class ArtistX(
    val image: List<ImageX>,
    val name: String,
    val url: String
)