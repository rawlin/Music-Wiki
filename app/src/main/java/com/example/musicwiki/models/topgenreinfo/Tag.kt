package com.example.musicwiki.models.topgenreinfo


import com.google.gson.annotations.SerializedName

data class Tag(
    val name: String,
    val reach: Int,
    val total: Int,
    val wiki: Wiki
)