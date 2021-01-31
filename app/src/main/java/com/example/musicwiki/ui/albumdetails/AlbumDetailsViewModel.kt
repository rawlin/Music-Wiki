package com.example.musicwiki.ui.albumdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicwiki.models.albumdetails.Album
import com.example.musicwiki.models.albumdetails.AlbumDetailsResponse
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AlbumDetailsViewModel(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModel() {

    private val _albumDetails: MutableLiveData<Resource<Album>> = MutableLiveData()
    val albumDetails: LiveData<Resource<Album>> = _albumDetails

    fun getAlbumDetails(artist: String, album: String) = viewModelScope.launch {
        _albumDetails.postValue(Resource.Loading())
        val response = musicWikiRepository.getAlbumDetails(artist = artist, album = album)
        _albumDetails.postValue(handleAlbumDetailsResponse(response))
    }

    private fun handleAlbumDetailsResponse(response: Response<AlbumDetailsResponse>): Resource<Album> {
        if (response.isSuccessful) {
            val result = response.body()!!.album
            return Resource.Success(result)
        }
        return Resource.Error(response.message())
    }
}