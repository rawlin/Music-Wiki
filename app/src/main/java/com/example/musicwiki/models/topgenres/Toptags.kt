package com.example.musicwiki.models.topgenres


import com.example.musicwiki.models.topgenres.Attr
import com.example.musicwiki.models.topgenres.Tag
import com.google.gson.annotations.SerializedName

data class Toptags(
    @SerializedName("@attr")
    val attr: Attr,
    val tag: List<Tag>
)