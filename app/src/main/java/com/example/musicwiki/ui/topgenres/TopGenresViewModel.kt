package com.example.musicwiki.ui.topgenres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicwiki.models.topgenres.TopGenresResponse
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class TopGenresViewModel(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModel() {

    private val _topGenres: MutableLiveData<Resource<TopGenresResponse>> = MutableLiveData()
    val topGenres: LiveData<Resource<TopGenresResponse>> = _topGenres


    init {
        getTopGenres()
    }

    private fun getTopGenres() = viewModelScope.launch {
        _topGenres.postValue(Resource.Loading())
        val response = musicWikiRepository.getAllGenres()
        _topGenres.postValue(handleTopGenresResponse(response))

    }

    private fun handleTopGenresResponse(response: Response<TopGenresResponse>): Resource<TopGenresResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

}