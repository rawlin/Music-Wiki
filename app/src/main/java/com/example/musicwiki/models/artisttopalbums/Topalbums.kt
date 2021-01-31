package com.example.musicwiki.models.artisttopalbums


import com.google.gson.annotations.SerializedName

data class Topalbums(
    val album: List<Album>,
    @SerializedName("@attr")
    val attr: Attr
)