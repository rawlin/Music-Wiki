package com.example.musicwiki.ui.detailsandlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicwiki.models.topgenreinfo.GenreInfoResponse
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsAndListsViewModel(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModel() {

    private val _info: MutableLiveData<Resource<String>> = MutableLiveData()
    val info: LiveData<Resource<String>> = _info


    fun getGenreInfo(genre: String) = viewModelScope.launch {
        _info.postValue(Resource.Loading())
        val response = musicWikiRepository.getGenreInfo(genre)
        _info.postValue(handleGenreInfoResponse(response))
    }

    private fun handleGenreInfoResponse(response: Response<GenreInfoResponse>): Resource<String>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                val info = resultResponse.tag.wiki.summary
                return Resource.Success(info)
            }
        }
        return Resource.Error(response.message())
    }
}