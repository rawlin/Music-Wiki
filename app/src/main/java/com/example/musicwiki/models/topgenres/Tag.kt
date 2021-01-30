package com.example.musicwiki.models.topgenres


import com.google.gson.annotations.SerializedName

data class Tag(
    val count: Int,
    val name: String,
    val reach: Int
)