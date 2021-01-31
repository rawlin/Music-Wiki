package com.example.musicwiki.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.musicwiki.ui.detailsandlists.albumlist.AlbumListFragment
import com.example.musicwiki.ui.detailsandlists.artistlist.ArtistListFragment
import com.example.musicwiki.ui.detailsandlists.tracklist.TrackListFragment

class ListsPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager,
    internal var totalTabs: Int
) : FragmentPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return AlbumListFragment()
            }
            1 -> {
                return ArtistListFragment()
            }
            2 -> {
                return TrackListFragment()
            }
            else -> return AlbumListFragment()
        }
    }

    override fun getCount(): Int =
        totalTabs
}