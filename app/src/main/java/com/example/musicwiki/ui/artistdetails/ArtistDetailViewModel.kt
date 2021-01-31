package com.example.musicwiki.ui.artistdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicwiki.models.artistdetails.Artist
import com.example.musicwiki.models.artistdetails.ArtistDetailsResponse
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ArtistDetailViewModel(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModel() {

    private val _artistDetails: MutableLiveData<Resource<Artist>> = MutableLiveData()
    val artistDetails: LiveData<Resource<Artist>> = _artistDetails

    fun getArtistDetails(artist: String) = viewModelScope.launch {
        _artistDetails.postValue(Resource.Loading())
        val response = musicWikiRepository.getArtistDetails(artist)
        _artistDetails.postValue(handleArtistDetailsResponse(response))
    }

    private fun handleArtistDetailsResponse(response: Response<ArtistDetailsResponse>): Resource<Artist> {
        if (response.isSuccessful) {
            val data = response.body()!!.artist
            return Resource.Success(data)
        }
        return Resource.Error(response.message())
    }


}