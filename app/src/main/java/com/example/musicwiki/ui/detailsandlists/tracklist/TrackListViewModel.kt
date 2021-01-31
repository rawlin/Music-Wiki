package com.example.musicwiki.ui.detailsandlists.tracklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicwiki.models.toptracks.TopTracksResponse
import com.example.musicwiki.models.toptracks.Track
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class TrackListViewModel(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModel() {

    private val _trackDetails: MutableLiveData<Resource<List<Track>>> = MutableLiveData()
    val trackDetails: LiveData<Resource<List<Track>>> = _trackDetails

    fun getTopTrackDetails(genre: String) = viewModelScope.launch {
        _trackDetails.postValue(Resource.Loading())
        val response = musicWikiRepository.getTopTracksInfo(genre)
        _trackDetails.postValue(handleTopTrackResponse(response))
    }

    private fun handleTopTrackResponse(response: Response<TopTracksResponse>): Resource<List<Track>> {
        if (response.isSuccessful) {
            val list = response.body()!!.tracks.track
            return (Resource.Success(list))
        }
        return Resource.Error(response.message())
    }
}