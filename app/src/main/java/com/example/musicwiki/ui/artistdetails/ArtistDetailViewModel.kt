package com.example.musicwiki.ui.artistdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicwiki.models.artistdetails.Artist
import com.example.musicwiki.models.artistdetails.ArtistDetailsResponse
import com.example.musicwiki.models.artisttopalbums.Album
import com.example.musicwiki.models.artisttopalbums.ArtistTopAlbumsResponse
import com.example.musicwiki.models.artisttoptracks.ArtistTopTracksResponse
import com.example.musicwiki.models.artisttoptracks.Track
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class ArtistDetailViewModel(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModel() {

    private val _artistDetails: MutableLiveData<Resource<Artist>> = MutableLiveData()
    val artistDetails: LiveData<Resource<Artist>> = _artistDetails

    private val _albumDetails: MutableLiveData<Resource<List<Album>>> = MutableLiveData()
    val albumDetails: LiveData<Resource<List<Album>>> = _albumDetails

    private val _trackDetails: MutableLiveData<Resource<List<Track>>> = MutableLiveData()
    val trackDetails: LiveData<Resource<List<Track>>> = _trackDetails

    fun getArtistDetails(artist: String) = viewModelScope.launch {
        _artistDetails.postValue(Resource.Loading())
        try {
            val artistDetailsDeferred = async { musicWikiRepository.getArtistDetails(artist) }
            val albumDetailsDeferred = async { musicWikiRepository.getAlbumsOfArtist(artist) }
            val trackDetailsDeferred = async { musicWikiRepository.getTracksOfArtist(artist) }

            val artistDetails = artistDetailsDeferred.await()
            val albumDetails = albumDetailsDeferred.await()
            val trackDetails = trackDetailsDeferred.await()

            _artistDetails.postValue(handleArtistDetailsResponse(artistDetails))
            _albumDetails.postValue(handleAlbumDetailsResponse(albumDetails))
            _trackDetails.postValue(handleTrackDetailsResponse(trackDetails))

        } catch (e: Exception) {
            _artistDetails.postValue(Resource.Error("Some Error Occurred"))
        }
    }

    private fun handleTrackDetailsResponse(response: Response<ArtistTopTracksResponse>): Resource<List<Track>> {
        if (response.isSuccessful) {
            val data = response.body()!!.toptracks.track
            return Resource.Success(data)
        }
        return Resource.Error(response.message())
    }

    private fun handleAlbumDetailsResponse(response: Response<ArtistTopAlbumsResponse>): Resource<List<Album>> {
        if (response.isSuccessful) {
            val data = response.body()!!.topalbums.album
            return Resource.Success(data)
        }
        return Resource.Error(response.message())
    }


    private fun handleArtistDetailsResponse(response: Response<ArtistDetailsResponse>): Resource<Artist> {
        if (response.isSuccessful) {
            val data = response.body()!!.artist
            return Resource.Success(data)
        }
        return Resource.Error(response.message())
    }


}