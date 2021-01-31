package com.example.musicwiki.ui.albumdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.adapters.AlbumGenreListAdapter
import com.example.musicwiki.databinding.ActivityAlbumDetailsBinding
import com.example.musicwiki.models.albumdetails.Album
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.ui.detailsandlists.DetailsAndListsActivity
import com.example.musicwiki.util.Resource

class AlbumDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumDetailsBinding
    private lateinit var viewModel: AlbumDetailsViewModel
    private lateinit var genreAdapter: AlbumGenreListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val musicWikiRepository = MusicWikiRepository()
        viewModel = ViewModelProvider(
            this,
            AlbumDetailsViewModelProviderFactory(musicWikiRepository)
        )
            .get(AlbumDetailsViewModel::class.java)
        setupRecyclerView()

        val artistName = intent.getStringExtra("artistName") ?: "Coldplay"
        val albumName = intent.getStringExtra("albumName") ?: "Parachutes"

        viewModel.getAlbumDetails(artist = artistName, album = albumName)

        viewModel.albumDetails.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    val result = response.data
                    createView(result!!)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        genreAdapter.setOnItemClickListener {
            val genreName = it
            val intent = Intent(this, DetailsAndListsActivity::class.java)
            intent.putExtra("Genre",genreName)
            startActivity(intent)
        }


    }

    private fun setupRecyclerView() {
        genreAdapter = AlbumGenreListAdapter()
        binding.rvAlbumDetail.apply {
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(this@AlbumDetailsActivity, RecyclerView.HORIZONTAL, false)

        }
    }

    private fun createView(result: Album) {
        val albumName = result.name
        val artistName = result.artist
        val desc = result.wiki.summary ?: "No Description"
        val imageUrl = result.image[4].text
        val descWithoutTags = removeTags(desc)

        genreAdapter.differ.submitList(result.tags.tag)

        Glide.with(this@AlbumDetailsActivity.applicationContext)
            .load(imageUrl)
            .error(R.drawable.empty)
            .thumbnail(
                Glide.with(applicationContext)
                    .load(R.drawable.empty)
            )
            .into(binding.imageView2)

        binding.apply {
            tvAlbumDetailName.text = albumName
            tvAlbumDetailArtist.text = artistName
            tvAlbumDetailDesc.text = descWithoutTags
        }
    }

    private fun removeTags(s: String) : String{
        val length = s.length
        var a:Int = 0
        for (i in s.indices) {
            if (s[i] == '<') {
                a = i
                break
            }
        }
        return s.substring(0, a)
    }

    private fun hideProgressBar() {
        binding.pbAlbumDetails.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.pbAlbumDetails.visibility = View.VISIBLE
    }
}