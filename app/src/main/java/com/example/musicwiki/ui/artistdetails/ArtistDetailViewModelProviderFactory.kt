package com.example.musicwiki.ui.artistdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicwiki.repository.MusicWikiRepository

class ArtistDetailViewModelProviderFactory(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArtistDetailViewModel(musicWikiRepository) as T
    }
}