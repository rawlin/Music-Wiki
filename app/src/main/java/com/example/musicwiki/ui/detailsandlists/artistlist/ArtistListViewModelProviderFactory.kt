package com.example.musicwiki.ui.detailsandlists.artistlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicwiki.repository.MusicWikiRepository

class ArtistListViewModelProviderFactory(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArtistListViewModel(musicWikiRepository) as T
    }
}