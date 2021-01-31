package com.example.musicwiki.ui.detailsandlists.tracklist

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicwiki.R
import com.example.musicwiki.adapters.TrackListAdapter
import com.example.musicwiki.databinding.FragmentTrackListBinding
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource

class TrackListFragment : Fragment(R.layout.fragment_track_list) {

    private lateinit var binding: FragmentTrackListBinding
    private lateinit var genre: String
    private lateinit var viewModel: TrackListViewModel
    private lateinit var trackAdapter: TrackListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        genre = this.arguments?.getString("Genre") ?: "Pop"
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackListBinding.bind(view)
        val musicWikiRepository = MusicWikiRepository()
        viewModel = ViewModelProvider(
            this,
            TrackListViewModelProviderFactory(musicWikiRepository)
        )
            .get(TrackListViewModel::class.java)
        setupRecyclerView()

        viewModel.getTopTrackDetails(genre)

        viewModel.trackDetails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    val list = response.data
                    trackAdapter.differ.submitList(list)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(this.context, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.pbTrackList.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.pbTrackList.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        trackAdapter = TrackListAdapter()
        binding.rvTrackList.apply {
            adapter = trackAdapter
            layoutManager = LinearLayoutManager(this@TrackListFragment.context)
        }
    }
}