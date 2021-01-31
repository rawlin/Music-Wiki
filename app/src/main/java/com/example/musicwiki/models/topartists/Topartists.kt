package com.example.musicwiki.models.topartists


import com.google.gson.annotations.SerializedName

data class Topartists(
    val artist: List<Artist>,
    @SerializedName("@attr")
    val attr: AttrX
)