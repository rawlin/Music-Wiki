package com.example.musicwiki.ui.detailsandlists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter
import com.example.musicwiki.R
import com.example.musicwiki.adapters.ListsPagerAdapter
import com.example.musicwiki.databinding.ActivityDetailsAndListsBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_details_and_lists.*

class DetailsAndListsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsAndListsBinding
    private lateinit var listPagerAdapter: ListsPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsAndListsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listPagerAdapter =
            ListsPagerAdapter(this, supportFragmentManager, binding.tabLayout.tabCount)
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
    }
}