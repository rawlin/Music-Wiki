package com.example.musicwiki.ui.detailsandlists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.musicwiki.R
import com.example.musicwiki.adapters.ListsPagerAdapter
import com.example.musicwiki.databinding.ActivityDetailsAndListsBinding
import com.example.musicwiki.repository.MusicWikiRepository
import com.example.musicwiki.util.Resource
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_details_and_lists.*
import java.util.*

class DetailsAndListsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsAndListsBinding
    private lateinit var listPagerAdapter: ListsPagerAdapter
    private lateinit var viewModel: DetailsAndListsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsAndListsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genre = intent.getStringExtra("Genre")
        val musicWikiRepository = MusicWikiRepository()
        viewModel = ViewModelProvider(
            this,
            DetailsAndListsViewModelProviderFactory(musicWikiRepository)
        )
            .get(DetailsAndListsViewModel::class.java)

        val title = genre!!.toLowerCase(Locale.getDefault()).capitalize()
        binding.tvInfoTitle.text = title

        viewModel.getGenreInfo(genre!!)

        listPagerAdapter =
            ListsPagerAdapter(this, supportFragmentManager, binding.tabLayout.tabCount, genre)
        binding.viewPager.adapter = listPagerAdapter

        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // NOT-USED
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // NOT-USED
            }
        })

        viewModel.info.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    val info = response.data
                    binding.tvInfoDesc.text = info
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
    }

    private fun hideProgressBar() {
        binding.pbDetailsAndLists.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.pbDetailsAndLists.visibility = View.VISIBLE
    }

}