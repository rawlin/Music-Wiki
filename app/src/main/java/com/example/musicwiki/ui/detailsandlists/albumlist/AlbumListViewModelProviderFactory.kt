package com.example.musicwiki.ui.detailsandlists.albumlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicwiki.repository.MusicWikiRepository

class AlbumListViewModelProviderFactory(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumListViewModel(musicWikiRepository) as T
    }
}