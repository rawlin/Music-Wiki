package com.example.musicwiki.ui.detailsandlists.tracklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicwiki.repository.MusicWikiRepository

class TrackListViewModelProviderFactory(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TrackListViewModel(musicWikiRepository) as T
    }
}