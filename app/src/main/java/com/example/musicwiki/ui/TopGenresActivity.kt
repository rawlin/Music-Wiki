package com.example.musicwiki.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicwiki.adapters.GenreAdapter
import com.example.musicwiki.databinding.ActivityTopGenresBinding

class TopGenresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopGenresBinding
    private lateinit var genreAdapter: GenreAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        genreAdapter = GenreAdapter()
        binding.rvGenre.apply {
            adapter = genreAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }
}