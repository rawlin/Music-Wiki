package com.example.musicwiki.repository

import com.example.musicwiki.network.RetrofitInstance

class MusicWikiRepository {

    suspend fun getAllGenres() =
        RetrofitInstance.api.getTopGenres()

    suspend fun getGenreInfo(genre: String) =
        RetrofitInstance.api.getGenreInfo(tag = genre)
}