package com.example.musicwiki.models.topgenres

import com.google.gson.annotations.SerializedName

data class Attr(
    @SerializedName("num_res")
    val numRes: Int,
    val offset: Int,
    val total: Int
)
