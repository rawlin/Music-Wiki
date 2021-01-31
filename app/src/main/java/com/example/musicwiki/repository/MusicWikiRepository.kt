package com.example.musicwiki.repository

import com.example.musicwiki.network.RetrofitInstance

class MusicWikiRepository {

    suspend fun getAllGenres() =
        RetrofitInstance.api.getTopGenres()

    suspend fun getGenreInfo(genre: String) =
        RetrofitInstance.api.getGenreInfo(tag = genre)

    suspend fun getTopAlbumInfo(genre: String) =
        RetrofitInstance.api.getTopAlbumsInfo(tag = genre)

    suspend fun getTopTracksInfo(genre: String) =
        RetrofitInstance.api.getTopTracksInfo(tag = genre)

    suspend fun getTopArtistsInfo(genre: String) =
        RetrofitInstance.api.getTopArtistsInfo(tag = genre)
}