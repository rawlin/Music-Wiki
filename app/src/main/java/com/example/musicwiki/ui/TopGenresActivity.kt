package com.example.musicwiki.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicwiki.adapters.GenreAdapter
import com.example.musicwiki.databinding.ActivityTopGenresBinding
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource

class TopGenresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopGenresBinding
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var viewModel: TopGenresViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val musicWikiRepository = MusicWikiRepository()
        viewModel = ViewModelProvider(this,
            TopGenresViewModelProviderFactory(musicWikiRepository))
            .get(TopGenresViewModel::class.java)
        setupRecyclerView()

        viewModel.topGenres.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    val list = response.data?.toptags?.tag
                    genreAdapter.differ.submitList(list)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                }
            }
        })


    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView() {
        genreAdapter = GenreAdapter()
        binding.rvGenre.apply {
            adapter = genreAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }
}