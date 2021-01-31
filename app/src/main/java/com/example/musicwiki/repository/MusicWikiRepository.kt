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

    suspend fun getAlbumDetails(artist: String, album: String) =
        RetrofitInstance.api.getAlbumDetails(artist = artist, album = album)

    suspend fun getArtistDetails(artist: String) =
        RetrofitInstance.api.getArtistDetails(artist = artist)

    suspend fun getAlbumsOfArtist(artist: String) =
        RetrofitInstance.api.getAlbumsOfArtist(artist = artist)

    suspend fun getTracksOfArtist(artist: String) =
        RetrofitInstance.api.getTracksOfArtist(artist = artist)
}