package com.example.musicwiki.ui.detailsandlists.artistlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicwiki.models.topartists.Artist
import com.example.musicwiki.models.topartists.TopArtistsResponse
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ArtistListViewModel(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModel() {

    private val _artistDetails: MutableLiveData<Resource<List<Artist>>> = MutableLiveData()
    val artistDetails: LiveData<Resource<List<Artist>>> = _artistDetails

    fun getArtistDetails(genre: String) = viewModelScope.launch {
        _artistDetails.postValue(Resource.Loading())
        val response = musicWikiRepository.getTopArtistsInfo(genre)
        _artistDetails.postValue(handleTopArtistDetails(response))
    }

    private fun handleTopArtistDetails(response: Response<TopArtistsResponse>): Resource<List<Artist>> {
        if (response.isSuccessful) {
            val data = response.body()!!.topartists.artist
            return Resource.Success(data)
        }
        return Resource.Error(response.message())
    }

}