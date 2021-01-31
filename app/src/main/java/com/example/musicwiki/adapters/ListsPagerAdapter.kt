package com.example.musicwiki.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.musicwiki.ui.detailsandlists.albumlist.AlbumListFragment
import com.example.musicwiki.ui.detailsandlists.artistlist.ArtistListFragment
import com.example.musicwiki.ui.detailsandlists.tracklist.TrackListFragment

class ListsPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager,
    internal var totalTabs: Int,
    private val genre: String
) : FragmentPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("Genre",genre)
                val fragment = AlbumListFragment()
                fragment.arguments = bundle
                Log.d("Genre",genre)
                return fragment
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString("Genre",genre)
                ArtistListFragment().arguments = bundle
                return ArtistListFragment()
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString("Genre",genre)
                TrackListFragment().arguments = bundle
                return TrackListFragment()
            }
            else -> return AlbumListFragment()
        }
    }

    override fun getCount(): Int =
        totalTabs
}