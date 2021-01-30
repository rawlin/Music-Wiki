package com.example.musicwiki.network

import com.example.musicwiki.models.topgenreinfo.GenreInfoResponse
import com.example.musicwiki.models.topgenres.TopGenresResponse
import com.example.musicwiki.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicWikiApi {

    @GET("2.0")
    suspend fun getTopGenres(
        @Query("method")
        method: String = "tag.gettoptags",
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("format")
        format: String = "json"
    ): Response<TopGenresResponse>

    @GET("2.0")
    suspend fun getGenreInfo(
        @Query("method")
        method: String = "getinfo",
        @Query("tag")
        tag: String,
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("format")
        format: String = "json"
    ): Response<GenreInfoResponse>

}