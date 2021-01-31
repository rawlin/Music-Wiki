package com.example.musicwiki.ui.detailsandlists.albumlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicwiki.R
import com.example.musicwiki.adapters.AlbumListAdapter
import com.example.musicwiki.databinding.FragmentAlbumListBinding
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.ui.albumdetails.AlbumDetailsActivity
import com.example.musicwiki.ui.detailsandlists.DetailsAndListsActivity
import com.example.musicwiki.util.Resource

class AlbumListFragment : Fragment(R.layout.fragment_album_list) {

    private lateinit var binding: FragmentAlbumListBinding
    private lateinit var genre: String
    private lateinit var viewModel: AlbumListViewModel
    private lateinit var albumAdapter: AlbumListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        genre = this.arguments?.getString("Genre") ?: "blank"
        Log.d("Genre is:", genre)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumListBinding.bind(view)
        val musicWikiRepository = MusicWikiRepository()
        viewModel = ViewModelProvider(
            this,
            AlbumListViewModelProviderFactory(musicWikiRepository)
        )
            .get(AlbumListViewModel::class.java)
        Log.d("AlbumListFragment", "Initialized")
        setupRecyclerView()
        viewModel.getAlbumDetails(genre)


        viewModel.albumDetails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    val list = response.data
                    albumAdapter.differ.submitList(list)
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

        albumAdapter.setOnItemClickListener {
            val artistName = it.artist.name
            val albumName = it.name
            val intent = Intent(this.context, AlbumDetailsActivity::class.java)
            intent.putExtra("artistName", artistName)
            intent.putExtra("albumName", albumName)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        albumAdapter = AlbumListAdapter()
        binding.rvAlbumList.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(this@AlbumListFragment.context)
        }
    }

    private fun showProgressBar() {
        binding.pbAlbumList.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbAlbumList.visibility = View.INVISIBLE
    }

}