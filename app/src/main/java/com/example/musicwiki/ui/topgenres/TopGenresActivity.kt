package com.example.musicwiki.ui.topgenres

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicwiki.R
import com.example.musicwiki.adapters.GenreAdapter
import com.example.musicwiki.databinding.ActivityTopGenresBinding
import com.example.musicwiki.models.topgenres.Tag
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.ui.detailsandlists.DetailsAndListsActivity
import com.example.musicwiki.util.Resource

class TopGenresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopGenresBinding
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var viewModel: TopGenresViewModel
    private lateinit var list: List<Tag>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val musicWikiRepository = MusicWikiRepository()
        viewModel = ViewModelProvider(
            this,
            TopGenresViewModelProviderFactory(musicWikiRepository)
        )
            .get(TopGenresViewModel::class.java)
        setupRecyclerView()

        viewModel.topGenres.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    list = response.data?.toptags?.tag!!
                    val listToSubmit = list.subList(0, 12)
                    genreAdapter.differ.submitList(listToSubmit)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                }
            }
        })

        binding.imageView.apply {
            setTag(1)
            setOnClickListener {
                if (getTag() == 1) {
                    setImageResource(R.drawable.ic_up)
                    setTag(2)
                    sendList(false)
                } else {
                    setImageResource(R.drawable.ic_down)
                    setTag(1)
                    sendList(true)
                }
            }
        }

        genreAdapter.setOnItemClickListener {
            val genreName = it.name
            val intent = Intent(this, DetailsAndListsActivity::class.java)
            intent.putExtra("Genre",genreName)
            startActivity(intent)
        }


    }

    private fun sendList(flag: Boolean) {
        if (!flag) {
            val listToSubmit = list.subList(0, 12)
            genreAdapter.differ.submitList(listToSubmit)
        } else {
            genreAdapter.differ.submitList(list)
        }
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
            isNestedScrollingEnabled = false
            adapter = genreAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }
}