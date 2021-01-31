package com.example.musicwiki.ui.artistdetails

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
import com.example.musicwiki.adapters.*
import com.example.musicwiki.databinding.ActivityArtistDetailsBinding
import com.example.musicwiki.models.artistdetails.Artist
import com.example.musicwiki.models.artistdetails.Tag
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.ui.albumdetails.AlbumDetailsActivity
import com.example.musicwiki.ui.detailsandlists.DetailsAndListsActivity
import com.example.musicwiki.util.Resource

class ArtistDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArtistDetailsBinding
    private lateinit var viewModel: ArtistDetailViewModel
    private lateinit var tracksAdapter: TopTracksAdapter
    private lateinit var albumsAdapter: TopAlbumsAdapter
    private lateinit var genreAdapter: ArtistGenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val artistName = intent.getStringExtra("artistName") ?: "Coldplay"
        val musicWikiRepository = MusicWikiRepository()
        viewModel = ViewModelProvider(
            this,
            ArtistDetailViewModelProviderFactory(musicWikiRepository)
        )
            .get(ArtistDetailViewModel::class.java)

        viewModel.getArtistDetails(artistName)
        setupTracksAdapter()
        setupAlbumsAdapter()
        setupGenreAdapter()

        viewModel.artistDetails.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    val data = response.data!!
                    displayData(data)
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

        viewModel.albumDetails.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    val list = response.data
                    albumsAdapter.differ.submitList(list)
                }

            }
        })

        viewModel.trackDetails.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    val list = response.data
                    tracksAdapter.differ.submitList(list)
                }
            }
        })

        albumsAdapter.setOnItemClickListener {
            val artistName = it.artist.name
            val albumName = it.name
            val intent = Intent(this, AlbumDetailsActivity::class.java)
            intent.putExtra("artistName", artistName)
            intent.putExtra("albumName", albumName)
            startActivity(intent)
        }

        genreAdapter.setOnItemClickListener {
            val genreName = it
            val intent = Intent(this, DetailsAndListsActivity::class.java)
            intent.putExtra("Genre", genreName)
            startActivity(intent)
        }

    }


    private fun displayData(data: Artist) {
        val artistName = data.name
        val artistDesc = data.bio.summary
        val imageUrl = data.image[3].text
        val playCount = data.stats.playcount.toLong()
        val followers = data.stats.listeners.toLong()
        val list = data.tags.tag
        val descWithoutTags = removeTags(artistDesc)

        val formattedPlayCount = formatNumberToSting(playCount)
        val formattedFollowers = formatNumberToSting(followers)

        genreAdapter.differ.submitList(list)

        binding.apply {
            tvArtistName.text = artistName
            tvArtistDetailsDesc.text = descWithoutTags
            tvArtistDetailPlaycount.text = formattedPlayCount
            tvArtistDetailFollowers.text = formattedFollowers
        }

        Glide.with(this@ArtistDetailsActivity.applicationContext)
            .load(imageUrl)
            .error(R.drawable.empty)
            .thumbnail(
                Glide.with(applicationContext)
                    .load(R.drawable.empty)
            )
            .into(binding.imageView3)

    }

    private fun formatNumberToSting(number: Long): String {
        if (number < 1000) {
            return number.toString()
        } else if (number in 1001..999999) {
            val num = (number / 1000L).toInt()
            val result = num.toString() + "K"
            return result
        } else {
            val num = (number / 1_000_000).toInt().toString()
            return num + "M"
        }
    }

    private fun removeTags(s: String): String {
        val length = s.length
        var a: Int = 0
        for (i in s.indices) {
            if (s[i] == '<') {
                a = i
                break
            }
        }
        return s.substring(0, a)
    }

    private fun hideProgressBar() {
        binding.progressBar2.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar2.visibility = View.VISIBLE
    }

    private fun setupGenreAdapter() {
        genreAdapter = ArtistGenreAdapter()
        binding.rvArtistDetailGenres.apply {
            adapter = genreAdapter
            layoutManager =
                LinearLayoutManager(this@ArtistDetailsActivity, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setupAlbumsAdapter() {
        albumsAdapter = TopAlbumsAdapter()
        binding.rvArtistDetailsTopAlbums.apply {
            adapter = albumsAdapter
            layoutManager =
                LinearLayoutManager(this@ArtistDetailsActivity, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setupTracksAdapter() {
        tracksAdapter = TopTracksAdapter()
        binding.rvArtistDetailTopTracks.apply {
            adapter = tracksAdapter
            layoutManager =
                LinearLayoutManager(this@ArtistDetailsActivity, RecyclerView.HORIZONTAL, false)
        }
    }
}