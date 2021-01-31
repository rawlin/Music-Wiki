package com.example.musicwiki.ui.detailsandlists.albumlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicwiki.models.topalbums.Album
import com.example.musicwiki.models.topalbums.TopAlbumsResponse
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AlbumListViewModel(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModel() {

    private val _albumDetails: MutableLiveData<Resource<List<Album>>> = MutableLiveData()
    val albumDetails: LiveData<Resource<List<Album>>> = _albumDetails

    fun getAlbumDetails(genre: String) = viewModelScope.launch {
        _albumDetails.postValue(Resource.Loading())
        val response = musicWikiRepository.getTopAlbumInfo(genre)
        _albumDetails.postValue(handleTopAlbumDetails(response))
    }

    private fun handleTopAlbumDetails(response: Response<TopAlbumsResponse>): Resource<List<Album>> {
        if (response.isSuccessful) {
            val data = response.body()!!.albums.album
            return Resource.Success(data)
        }
        return Resource.Error(response.message())
    }
}