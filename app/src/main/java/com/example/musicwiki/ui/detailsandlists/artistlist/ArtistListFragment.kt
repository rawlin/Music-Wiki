package com.example.musicwiki.ui.detailsandlists.artistlist

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
import com.example.musicwiki.adapters.ArtistListAdapter
import com.example.musicwiki.databinding.FragmentArtistListBinding
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource

class ArtistListFragment : Fragment(R.layout.fragment_artist_list) {

    private lateinit var binding: FragmentArtistListBinding
    private lateinit var genre: String
    private lateinit var viewModel: ArtistListViewModel
    private lateinit var artistAdapter: ArtistListAdapter

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
        binding = FragmentArtistListBinding.bind(view)
        val musicWikiRepository = MusicWikiRepository()
        viewModel = ViewModelProvider(
            this,
            ArtistListViewModelProviderFactory(musicWikiRepository)
        )
            .get(ArtistListViewModel::class.java)
        setupRecyclerView()
        viewModel.getArtistDetails(genre)

        viewModel.artistDetails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    val list = response.data
                    artistAdapter.differ.submitList(list)
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
        binding.pbArtistList.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.pbArtistList.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        artistAdapter = ArtistListAdapter()
        binding.rvArtistList.apply {
            adapter = artistAdapter
            layoutManager = LinearLayoutManager(this@ArtistListFragment.context)
        }
    }
}