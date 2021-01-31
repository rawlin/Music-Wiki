package com.example.musicwiki.ui.albumdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicwiki.repository.MusicWikiRepository

class AlbumDetailsViewModelProviderFactory(
    private val musicWikiRepository: MusicWikiRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumDetailsViewModel(musicWikiRepository) as T
    }
}