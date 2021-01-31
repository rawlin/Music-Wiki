package com.example.musicwiki.models.albumdetails


import com.google.gson.annotations.SerializedName

data class Track(
    val artist: Artist,
    @SerializedName("@attr")
    val attr: Attr,
    val duration: String,
    val name: String,
    val streamable: Streamable,
    val url: String
)